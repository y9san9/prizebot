package me.y9san9.prizebot.actors.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.actors.telegram.sender.GiveawayCreatedSender
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.GiveawayConditions
import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import java.time.OffsetDateTime


object CreateGiveawayActor {
    suspend fun create (
        update: PrizebotMessageUpdate,
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

        GiveawayCreatedSender.send(update, giveaway)

        AutoRaffleActor.schedule(update.bot, giveaway, update.di)

        return stateResult(MainState)
    }
}
