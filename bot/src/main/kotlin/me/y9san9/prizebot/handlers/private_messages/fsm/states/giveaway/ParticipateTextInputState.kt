package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.actors.telegram.sender.CancellationSender
import me.y9san9.prizebot.actors.telegram.sender.GiveawayCreatedSender
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.resources.Emoji


@Serializable
data class GiveawayTitle (
    val title: String
)

object ParticipateTextInputState : PrizebotFSMState<GiveawayTitle> {
    override suspend fun process (data: GiveawayTitle, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {
        event.textOrDefault { text ->
            return result(MainState).apply {
                when (text) {
                    "/cancel" -> CancellationSender.send(event)
                    "/skip" -> createGiveaway(event, data.title, participateText = Emoji.HEART)
                    else -> createGiveaway(event, data.title, text)
                }
            }
        }

        return result(ParticipateTextInputState, data)
    }

    private suspend fun createGiveaway(update: PrizebotPrivateMessageUpdate, title: String, participateText: String) {
        val giveaway = update.di.saveGiveaway (
            update.chatId, title, participateText, update.languageCode
        )

        GiveawayCreatedSender.send(update, giveaway)
    }
}
