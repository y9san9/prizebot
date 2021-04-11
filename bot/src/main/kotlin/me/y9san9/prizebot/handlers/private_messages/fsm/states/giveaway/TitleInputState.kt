package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.telegram.sender.GiveawayParticipateTextInputSender
import me.y9san9.prizebot.actors.telegram.sender.TooLongGiveawayTitleSender
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.resources.MAX_TITLE_LEN


object TitleInputState : PrizebotFSMState<Unit> {
    override suspend fun process(data: Unit, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {

        event.textOrDefault { text ->
            return when {
                text == "/cancel" -> stateResult(MainState)
                    .apply { MainState.cancellation(event) }
                text.length > MAX_TITLE_LEN -> stateResult(TitleInputState)
                    .apply { TooLongGiveawayTitleSender.send(event) }
                else -> stateResult(ParticipateTextInputState, GiveawayTitle(title = text))
                    .apply { GiveawayParticipateTextInputSender.send(event) }
            }
        }

        return stateResult(TitleInputState)
    }
}
