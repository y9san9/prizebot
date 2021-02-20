package me.y9san9.prizebot

import dev.inmo.micro_utils.coroutines.subscribeSafely
import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.longPolling
import dev.inmo.tgbotapi.types.message.abstracts.PrivateContentMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import me.y9san9.fsm.FSM
import me.y9san9.fsm.statesOf
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawayStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.storage.states_storage.PrizebotFSMStorage
import me.y9san9.prizebot.handlers.callback_queries.CallbackQueryHandler
import me.y9san9.prizebot.handlers.inline_queries.InlineQueryHandler
import me.y9san9.prizebot.handlers.private_messages.fsm.prizebotPrivateMessages
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.ParticipateTextInputState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TitleInputState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.statesSerializers
import me.y9san9.prizebot.models.DatabaseConfig
import me.y9san9.prizebot.models.di.PrizebotDI
import me.y9san9.prizebot.models.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.telegram.updates.CallbackQueryUpdate
import me.y9san9.telegram.updates.InlineQueryUpdate
import org.jetbrains.exposed.sql.Database


class Prizebot (
    botToken: String,
    databaseConfig: DatabaseConfig?,
    private val scope: CoroutineScope
) {
    private val bot = telegramBot(botToken)
    private val database = connectDatabase(databaseConfig)
    private val storage = PrizebotFSMStorage(database, statesSerializers)

    fun start() = bot.longPolling {
        val di = PrizebotDI (
            giveawaysStorage = GiveawayStorage(database),
            participantsStorage = ParticipantsStorage(database)
        )

        val messages = messageFlow
            .mapNotNull { it.data as? PrivateContentMessage<*> }
            .map { PrizebotPrivateMessageUpdate(bot, di, it) }

        createFSM(events = messages)

        inlineQueryFlow
            .map { InlineQueryUpdate(bot, di, query = it) }
            .subscribeSafely(scope, ::logException, InlineQueryHandler::handle)

        callbackQueryFlow
            .map { CallbackQueryUpdate(bot, di, query = it) }
            .subscribeSafely(scope, ::logException, CallbackQueryHandler::handle)
    }

    private fun createFSM(events: Flow<PrizebotPrivateMessageUpdate>) = FSM.prizebotPrivateMessages (
        events,
        states = statesOf (
            initial = MainState,
            TitleInputState, ParticipateTextInputState
        ),
        storage = storage,
        scope = scope
    )

    private fun connectDatabase(config: DatabaseConfig?) = config?.run {
        if(driver != null) {
            Database.connect(url, driver, user, password)
        } else {
            Database.connect(url, user = user, password = password)
        }
    }

    private fun logException(throwable: Throwable) =
        System.err.println("Unexpected exception occurred: ${throwable.stackTraceToString()}")
}
