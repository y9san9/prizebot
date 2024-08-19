package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object TitleInputState : PrizebotFSMState<Unit> {
    override suspend fun process(data: Unit, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {

        event.textOrDefault { text ->
            return when {
                text == "/cancel" -> stateResult(MainState)
                    .apply { MainState.cancellation(event) }
                text.length > MAX_TITLE_LEN -> stateResult(TitleInputState)
                    .apply { event.sendMessage(event.getLocale().giveawayTitleTooLong) }
                else -> stateResult(ParticipateTextInputState, ParticipateTextInputData(title = text))
                    .apply { event.sendMessage(event.getLocale().giveawayParticipateInput) }
            }
        }

        return stateResult(TitleInputState)
    }
}
