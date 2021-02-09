package me.y9san9.prizebot.bot.states

import dev.inmo.tgbotapi.types.ParseMode.Markdown
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.bot.shortcuts.*
import me.y9san9.prizebot.bot.states.giveaway.TitleInputState
import me.y9san9.prizebot.resources.locales.Locale


object MainState : FSMInitialPrizebotState {

    override suspend fun process(data: Unit, event: PrizebotMessageUpdate): FSMStateResult<*> {
        event.commandOrDefault {
            case("/start") {
                event.sendMessage (
                    text = Locale.with(event.languageCode).start,
                    replyMarkup = mainMarkup(event.di, event.chatId, event.languageCode),
                    parseMode = Markdown
                )
            }
            raw("/help", Locale.with(event.languageCode).helpKeyboard) {
                event.sendMessage (
                    text = Locale.with(event.languageCode).help
                )
            }
            raw("/giveaway", Locale.with(event.languageCode).giveawayKeyboard) {
                event.sendMessage (
                    text = Locale.with(event.languageCode).giveawayTitleInput,
                    replyMarkup = ReplyKeyboardRemove()
                )
                return result(TitleInputState)
            }
            raw("/my_giveaways", Locale.with(event.languageCode).selfGiveawaysKeyboard) {
                event.sendSelfGiveawaysMessage()
            }
        }

        return result(MainState)
    }

}
