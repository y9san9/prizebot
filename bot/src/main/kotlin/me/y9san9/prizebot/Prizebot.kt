package me.y9san9.prizebot

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.LogLevel
import dev.inmo.kslog.common.defaultMessageFormatter
import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.longPolling
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.abstracts.PrivateContentMessage
import dev.inmo.tgbotapi.types.toChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import me.y9san9.db.migrations.MigrationsApplier
import me.y9san9.fsm.FSM
import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.actors.giveaway.RaffleActorV2
import me.y9san9.prizebot.conditions.TelegramConditionsClient
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.database.linked_channels_storage.LinkedChannelsStorage
import me.y9san9.prizebot.database.migrations.databaseMigrations
import me.y9san9.prizebot.database.states_storage.PrizebotFSMStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.prizebot.extensions.flow.launchEachSafelyByChatId
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.handlers.callback_queries.CallbackQueryHandler
import me.y9san9.prizebot.handlers.choosen_inline_result.ChosenInlineResultHandler
import me.y9san9.prizebot.handlers.inline_queries.InlineQueryHandler
import me.y9san9.prizebot.handlers.my_chat_member_updated.MyChatMemberUpdateHandler
import me.y9san9.prizebot.handlers.private_messages.fsm.prizebotPrivateMessages
import me.y9san9.prizebot.handlers.private_messages.fsm.states.prizebotStates
import me.y9san9.prizebot.handlers.private_messages.fsm.states.statesSerializers
import me.y9san9.prizebot.limiter.PrizebotRequestsLimiter
import me.y9san9.telegram.updates.*
import org.jetbrains.exposed.sql.Database

sealed interface DatabaseConfig {
    data class Actual(
        val url: String,
        val user: String,
        val password: String,
        val driver: String?
    ) : DatabaseConfig

    data object InMemory : DatabaseConfig
}

class Prizebot(
    botToken: String,
    randomOrgApiKey: String,
    databaseConfig: DatabaseConfig,
    private val logChatId: Long?,
    private val scope: CoroutineScope
) {
    private val bot = telegramBot(botToken) {
        requestsLimiter = PrizebotRequestsLimiter
        logger = KSLog { level: LogLevel, tag: String?, message: Any, throwable: Throwable? ->
            println(defaultMessageFormatter(level, tag, message, throwable))
        }
    }
    private val database = connectDatabase(databaseConfig)

    private val raffleActor = RaffleActorV2(randomOrgApiKey, scope)
    private val autoRaffleActor = AutoRaffleActor(raffleActor)

    private val di = PrizebotDI (
        giveawaysStorage = GiveawaysStorage(database, autoRaffleActor = autoRaffleActor),
        giveawaysActiveMessagesStorage = GiveawaysActiveMessagesStorage(database),
        languageCodesStorage = LanguageCodesStorage(database),
        linkedChannelsStorage = LinkedChannelsStorage(database),
        userTitlesStorage = UserTitlesStorage(database),
        raffleActor = raffleActor,
        autoRaffleActor = autoRaffleActor,
        conditionsClient = TelegramConditionsClient(scope, bot),
        scope = scope
    )

    fun start() = bot.longPolling {
        makeDatabaseMigrations()
        scheduleRaffles()

        val privateMessages = messagesFlow
            .mapNotNull { it.data as? PrivateContentMessage<*> }
            .map { PrivateMessageUpdate(bot, di, message = it) }

        createFSM(events = privateMessages)

        myChatMemberUpdatesFlow
            .map { MyChatMemberUpdate(bot, di, update = it) }
            .launchEachSafelyByChatId(scope, ::logException, MyChatMemberUpdateHandler::handle)

        inlineQueriesFlow
            .map { InlineQueryUpdate(bot, di, query = it) }
            .launchEachSafelyByChatId(scope, ::logException, InlineQueryHandler::handle)

        callbackQueriesFlow
            .map {
                println("RECEIVED! $it")
                CallbackQueryUpdate(bot, di, query = it)
            }
            .launchEachSafelyByChatId(scope, ::logException, CallbackQueryHandler::handle)

        chosenInlineResultsFlow
            .map { ChosenInlineResultUpdate(bot, di, update = it) }
            .launchEachSafelyByChatId(scope, ::logException, ChosenInlineResultHandler::handle)

        println("> Prizebot: System is up and running")
        scope.launch {
            val me = bot.getMe()
            println("> Prizebot: Bot Account: ${me.username?.username} (${me.id.chatId})")
        }
    }

    private fun scheduleRaffles() = scope.launch {
        autoRaffleActor.scheduleAll(bot, di)
    }

    private fun makeDatabaseMigrations() = MigrationsApplier.apply(database, databaseMigrations)

    private fun createFSM(events: Flow<PrizebotPrivateMessageUpdate>) = FSM.prizebotPrivateMessages (
        events,
        states = prizebotStates,
        storage = PrizebotFSMStorage(database, statesSerializers),
        scope = scope,
        throwableHandler = ::logException
    )

    private fun connectDatabase(config: DatabaseConfig) = config.run {
        when(this) {
            is DatabaseConfig.Actual -> if (driver != null) {
                Database.connect(url, driver, user, password)
            } else {
                Database.connect(url, user = user, password = password)
            }
            is DatabaseConfig.InMemory -> {
                Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
            }
        }
    }

    private suspend fun logException(throwable: Throwable) {
        val stacktrace = throwable.stackTraceToString()

        System.err.println("Unexpected exception occurred: $stacktrace")
        println("But still working")

        runCatching {
            bot.sendMessage(
                chatId = logChatId?.toChatId() ?: return,
                text = stacktrace
            )
        }
    }
}
