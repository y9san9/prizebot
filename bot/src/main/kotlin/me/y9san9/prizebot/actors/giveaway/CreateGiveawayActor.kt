package me.y9san9.prizebot.actors.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.actors.telegram.sender.GiveawaySender
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.GiveawayConditions
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import java.time.OffsetDateTime


object CreateGiveawayActor {
    suspend fun create (
        update: PrizebotPrivateMessageUpdate,
        title: String,
        participateText: String,
        raffleDate: OffsetDateTime?,
        winnersCount: WinnersCount,
        conditions: GiveawayConditions
    ): FSMStateResult<*> {

        val giveaway = update.di.saveGiveaway (
            update.userId, title, participateText,
            languageCode = update.di.getLanguageCode(update.userId) ?: update.languageCode,
            raffleDate, winnersCount, conditions
        )

        update.sendMessage(update.locale.giveawayCreated, replyMarkup = mainMarkup(update))
        GiveawaySender.send(update, update.di, giveaway, demo = true)

        AutoRaffleActor.schedule(update.bot, giveaway, update.di)

        return stateResult(MainState)
    }
}
