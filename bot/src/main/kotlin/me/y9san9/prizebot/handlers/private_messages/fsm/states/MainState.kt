package me.y9san9.prizebot.handlers.private_messages.fsm.states

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.telegram.sender.*
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TitleInputState
import me.y9san9.prizebot.extensions.telegram.commandOrDefault
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object MainState : PrizebotFSMState<Unit> {

    suspend fun cancellation(event: PrizebotMessageUpdate) = stateResult(MainState) {
        event.sendMessage (
            event.locale.cancelled,
            replyMarkup = mainMarkup(event)
        )
    }

    override suspend fun process(data: Unit, event: PrizebotMessageUpdate): FSMStateResult<*> {
        event.commandOrDefault {
            case("/start") {
                StartSender.send(event)

                // fixme: business logic in declarative code
                if (event.di.getLanguageCode(event.chatId) == null) {
                    SelectLocaleSender.send(event)
                    event.di.setLanguageCode (
                        event.chatId,
                        languageCode = event.languageCode ?: "en"
                    )
                }
            }
            case("/language") {
                SelectLocaleSender.send(event)
            }
            case("/help", Emoji.HELP) {
                HelpSender.send(event)
            }
            case("/giveaway", Emoji.GIFT) {
                GiveawayTitleInputSender.send(event)
                return stateResult(TitleInputState)
            }
            case("/my_giveaways", Emoji.SETTINGS) {
                SelfGiveawaysSender.send(event)
            }
        }

        return stateResult(MainState)
    }
}
