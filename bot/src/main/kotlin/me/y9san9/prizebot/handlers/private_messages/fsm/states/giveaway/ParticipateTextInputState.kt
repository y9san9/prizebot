package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


@SerialName("giveaway_title")
@Serializable
data class GiveawayTitle (
    val title: String
)

object ParticipateTextInputState : PrizebotFSMState<GiveawayTitle> {
    override suspend fun process (data: GiveawayTitle, event: PrizebotMessageUpdate): FSMStateResult<*> {
        event.textOrDefault { text ->
            return when (text) {
                "/cancel" -> MainState.cancellation(event)
                "/skip" -> raffleDateInput(event, data.title, participateText = Emoji.HEART)
                else -> raffleDateInput(event, data.title, text)
            }
        }

        return stateResult(ParticipateTextInputState, data)
    }

    private suspend fun raffleDateInput (
        update: PrizebotMessageUpdate,
        title: String,
        participateText: String
    ): FSMStateResult<*> {
        update.sendMessage(update.locale.enterRaffleDateInput)
        return stateResult(RaffleDateInputState, RaffleDateInputData(title, participateText))
    }
}
