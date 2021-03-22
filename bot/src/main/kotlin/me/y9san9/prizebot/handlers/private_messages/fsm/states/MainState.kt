package me.y9san9.prizebot.handlers.private_messages.fsm.states

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.actors.telegram.sender.*
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TitleInputState
import me.y9san9.prizebot.extensions.telegram.commandOrDefault
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.resources.Emoji


object MainState : PrizebotFSMState<Unit> {
    override suspend fun process(data: Unit, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {
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
                return result(TitleInputState)
            }
            case("/my_giveaways", Emoji.SETTINGS) {
                SelfGiveawaysSender.send(event)
            }
        }

        return result(MainState)
    }
}
