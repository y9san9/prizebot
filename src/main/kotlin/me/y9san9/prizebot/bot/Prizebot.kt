@file:Suppress("EXPERIMENTAL_API_USAGE")

package me.y9san9.prizebot.bot

import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.longPolling
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.startGettingFlowsUpdatesByLongPolling
import dev.inmo.tgbotapi.types.message.abstracts.PrivateMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import me.y9san9.fsm.FSM
import me.y9san9.fsm.statesOf
import me.y9san9.fsm.storage.KDSStorage
import me.y9san9.telegram.updates.FSMTelegramStorage
import me.y9san9.telegram.updates.callback_query.BotCallbackQueryUpdate
import me.y9san9.telegram.updates.inline_query.BotInlineQueryUpdate
import me.y9san9.telegram.updates.message.BotMessageUpdate
import me.y9san9.prizebot.bot.callback_query.handleCallbackQuery
import me.y9san9.prizebot.bot.di.PrizebotDI
import me.y9san9.prizebot.bot.inline_query.handleInlineQuery
import me.y9san9.prizebot.bot.shortcuts.PrizebotMessageUpdate
import me.y9san9.prizebot.bot.shortcuts.prizebot
import me.y9san9.prizebot.bot.states.MainState
import me.y9san9.prizebot.bot.states.giveaway.ParticipateInputState
import me.y9san9.prizebot.bot.states.giveaway.TitleInputState
import me.y9san9.prizebot.bot.states.statesSerializers
import me.y9san9.prizebot.logic.database.GiveawayStorage
import me.y9san9.prizebot.logic.database.ParticipantsStorage
import me.y9san9.prizebot.logic.database.PrizebotTableStorage
import org.jetbrains.exposed.sql.Database


data class DatabaseConfig (
    val url: String,
    val user: String,
    val password: String,
    val driver: String?
)

class Prizebot (
    botToken: String,
    databaseConfig: DatabaseConfig?,
    private val scope: CoroutineScope
) : CoroutineScope by scope {

    private val database = databaseConfig?.run {
        if(driver != null) {
            Database.connect(url, driver, user, password)
        } else {
            Database.connect(url, user = user, password = password)
        }
    }

    private val storage: FSMTelegramStorage =
        if(database == null) {
            KDSStorage(statesSerializers)
        } else {
            PrizebotTableStorage(database, statesSerializers)
        }

    private val bot = telegramBot(token = botToken)

    fun start() {
        bot.longPolling {
            val di = PrizebotDI (
                giveawaysStorage = GiveawayStorage(database),
                participantsStorage = ParticipantsStorage(database)
            )

            val messages = messageFlow
                .map { it.data }
                .filterIsInstance<PrivateMessage<*>>()
                .map { BotMessageUpdate(bot, di, update = it) }

            createFSM(events = messages)

            inlineQueryFlow
                .map { BotInlineQueryUpdate(bot, di, update = it) }
                .onEach(::handleInlineQuery)
                .launchIn(scope)

            callbackQueryFlow
                .map { BotCallbackQueryUpdate(bot, di, update = it) }
                .onEach(::handleCallbackQuery)
                .launchIn(scope)
        }
    }

    private fun createFSM(events: Flow<PrizebotMessageUpdate>) = FSM.prizebot (
        events,
        states = statesOf (
            initial = MainState,
            TitleInputState, ParticipateInputState
        ),
        storage = storage,
        scope = scope
    )
}
