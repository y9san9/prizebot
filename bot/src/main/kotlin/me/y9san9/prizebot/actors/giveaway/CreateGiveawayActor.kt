package me.y9san9.prizebot.actors.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.actors.telegram.sender.GiveawayCreatedSender
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import java.time.OffsetDateTime


object CreateGiveawayActor {
    suspend fun create (
        update: PrizebotPrivateMessageUpdate,
        title: String,
        participateText: String,
        raffleDate: OffsetDateTime?,
        winnersCount: WinnersCount
    ): FSMStateResult<*> {

        val giveaway = update.di.saveGiveaway (
            update.chatId, title, participateText,
            languageCode = update.di.getLanguageCode(update.chatId) ?: update.languageCode,
            raffleDate, winnersCount
        )

        GiveawayCreatedSender.send(update, giveaway)

        AutoRaffleActor.schedule(update.bot, giveaway, update.di)

        return stateResult(MainState)
    }
}
