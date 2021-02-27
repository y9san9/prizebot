package me.y9san9.prizebot.handlers.private_messages.fsm.states

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.actors.telegram.sender.GiveawayTitleInputSender
import me.y9san9.prizebot.actors.telegram.sender.HelpSender
import me.y9san9.prizebot.actors.telegram.sender.SelfGiveawaysSender
import me.y9san9.prizebot.actors.telegram.sender.StartSender
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TitleInputState
import me.y9san9.prizebot.extensions.telegram.commandOrDefault
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate


object MainState : PrizebotFSMState<Unit> {
    override suspend fun process(data: Unit, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {
        val locale = event.locale

        event.commandOrDefault {
            case("/start") {
                StartSender.send(event)
            }
            raw("/help", locale.helpKeyboard) {
                HelpSender.send(event)
            }
            raw("/giveaway", locale.giveawayKeyboard) {
                GiveawayTitleInputSender.send(event)
                return result(TitleInputState)
            }
            raw("/my_giveaways", locale.selfGiveawaysKeyboard) {
                SelfGiveawaysSender.send(event)
            }
        }

        return result(MainState)
    }
}
