package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.extensions.utils.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import me.y9san9.extensions.string.times
import me.y9san9.prizebot.resources.CALLBACK_NO_ACTION
import me.y9san9.prizebot.resources.Emoji


fun raffleProcessingMarkup() = InlineKeyboardMarkup (
    CallbackDataInlineKeyboardButton (
        text = Emoji.HOURGLASS,
        callbackData = "$CALLBACK_NO_ACTION"
    )
)
