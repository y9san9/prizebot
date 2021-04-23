package me.y9san9.prizebot

import dev.inmo.micro_utils.coroutines.subscribeSafely
import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.longPolling
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.abstracts.ChannelContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.PrivateContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.PublicContentMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.y9san9.fsm.FSM
import me.y9san9.fsm.statesOf
import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.database.linked_channels_storage.LinkedChannelsStorage
import me.y9san9.prizebot.database.states_storage.PrizebotFSMStorage
import me.y9san9.prizebot.handlers.callback_queries.CallbackQueryHandler
import me.y9san9.prizebot.handlers.choosen_inline_result.ChosenInlineResultHandler
import me.y9san9.prizebot.handlers.inline_queries.InlineQueryHandler
import me.y9san9.prizebot.handlers.private_messages.fsm.prizebotPrivateMessages
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.statesSerializers
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.handlers.channel_posts.ChannelPostsHandler
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.*
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.InvitationsCountInputState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.SubscriptionChannelInputState
import me.y9san9.telegram.updates.CallbackQueryUpdate
import me.y9san9.telegram.updates.ChosenInlineResultUpdate
import me.y9san9.telegram.updates.ChannelPostUpdate
import me.y9san9.telegram.updates.InlineQueryUpdate
import org.jetbrains.exposed.sql.Database


data class DatabaseConfig (
    val url: String,
    val user: String,
    val password: String,
    val driver: String?
)

class Prizebot (
    botToken: String,
    databaseConfig: DatabaseConfig,
    private val logChatId: Long?,
    private val scope: CoroutineScope
) {
    private val bot = telegramBot(botToken)
    private val database = connectDatabase(databaseConfig)

    fun start() = bot.longPolling {
        val di = PrizebotDI (
            giveawaysStorage = GiveawaysStorage(database),
            giveawaysActiveMessagesStorage = GiveawaysActiveMessagesStorage(database),
            languageCodesStorage = LanguageCodesStorage(database),
            linkedChannelsStorage = LinkedChannelsStorage(database)
        )
        scheduleRaffles(bot, di)

        val privateMessages = messageFlow
            .mapNotNull { it.data as? PrivateContentMessage<*> }
            .map { PrizebotPrivateMessageUpdate(bot, di, message = it) }

        createFSM(events = privateMessages)

        chatMemberUpdatedFlow
            .onEach { println(it) }
            .launchIn(scope)

        channelPostFlow
            .mapNotNull { it.data as? ChannelContentMessage<*> }
            .map { ChannelPostUpdate(bot, di, message = it) }
            .subscribeSafely(scope, ::logException, ChannelPostsHandler::handle)

        inlineQueryFlow
            .map { InlineQueryUpdate(bot, di, query = it) }
            .subscribeSafely(scope, ::logException, InlineQueryHandler::handle)

        callbackQueryFlow
            .map { CallbackQueryUpdate(bot, di, query = it) }
            .subscribeSafely(scope, ::logException, CallbackQueryHandler::handle)

        chosenInlineResultFlow
            .map { ChosenInlineResultUpdate(bot, di, update = it) }
            .subscribeSafely(scope, ::logException, ChosenInlineResultHandler::handle)
    }

    private fun scheduleRaffles(bot: TelegramBot, di: PrizebotDI) = scope.launch {
        AutoRaffleActor.scheduleAll(bot, di)
    }

    private fun createFSM(events: Flow<PrizebotPrivateMessageUpdate>) = FSM.prizebotPrivateMessages (
        events,
        states = statesOf (
            initial = MainState,
            TitleInputState, ParticipateTextInputState,
            RaffleDateInputState, TimezoneInputState,
            CustomTimezoneInputState, WinnersCountInputState,
            ConditionInputState, InvitationsCountInputState,
            SubscriptionChannelInputState
        ),
        storage = PrizebotFSMStorage(database, statesSerializers),
        scope = scope,
        throwableHandler = ::logException
    )

    private fun connectDatabase(config: DatabaseConfig) = config.run {
        if(driver != null) {
            Database.connect(url, driver, user, password)
        } else {
            Database.connect(url, user = user, password = password)
        }
    }

    private suspend fun logException(throwable: Throwable) {
        val stacktrace = throwable.stackTraceToString()

        System.err.println("Unexpected exception occurred: $stacktrace")
        println("But still working")

        logChatId ?: return
        bot.sendMessage(ChatId(logChatId), stacktrace)
    }
}
