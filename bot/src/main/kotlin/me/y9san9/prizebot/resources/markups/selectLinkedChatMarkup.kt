package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale


fun selectLinkedChatMarkup(update: PrizebotLocalizedBotUpdate, usernames: List<String>): ReplyKeyboardMarkup {
    val linkedChats = usernames
        .map(::SimpleKeyboardButton)
        .chunked(size = 4)

    return ReplyKeyboardMarkup (
        keyboard = listOf (
            listOf(SimpleKeyboardButton(update.locale.updateChannels))
        ) + linkedChats,
        resizeKeyboard = true
    )
}
