package me.y9san9.prizebot.handlers.private_messages.fsm.states

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.telegram.sender.SelectLocaleSender
import me.y9san9.prizebot.actors.telegram.sender.SelfGiveawaysSender
import me.y9san9.prizebot.actors.telegram.sender.StartSender
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.commandOrDefault
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TitleInputState
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object MainState : PrizebotFSMState<Unit> {

    suspend fun cancellation(event: PrizebotPrivateMessageUpdate) = stateResult(MainState) {
        event.sendMessage (
            event.getLocale().cancelled,
            replyMarkup = mainMarkup(event)
        )
    }

    override suspend fun process(data: Unit, event: PrizebotPrivateMessageUpdate): FSMStateResult<*> {
        event.commandOrDefault (
            noTextMatchedMatched = {
                event.sendMessage(event.getLocale().unknownCommand(it.actualText), replyMarkup = mainMarkup(event))
            }
        ) {
            case("/start") {
                StartSender.send(event)

                // fixme: business logic in declarative code
                if (event.di.getLanguageCode(event.userId) == null) {
                    SelectLocaleSender.send(event)
                    event.di.setLanguageCode (
                        event.userId,
                        languageCode = event.languageCode ?: "en"
                    )
                }
            }
            case("/language") {
                SelectLocaleSender.send(event)
            }
            case("/help", Emoji.HELP) {
                event.sendMessage(event.getLocale().help)
            }
            case("/giveaway", Emoji.GIFT) {
                event.sendMessage (
                    text = event.getLocale().giveawayTitleInput,
                    replyMarkup = ReplyKeyboardRemove()
                )
                return stateResult(TitleInputState)
            }
            case("/my_giveaways", Emoji.SETTINGS) {
                SelfGiveawaysSender.send(event)
            }
        }

        return stateResult(MainState)
    }
}
