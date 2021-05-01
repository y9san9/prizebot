package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.extensions.utils.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.resources.locales.Locale


fun yesNoMarkup(locale: Locale) = ReplyKeyboardMarkup (
    SimpleKeyboardButton(locale.yes),
    SimpleKeyboardButton(locale.no),
    resizeKeyboard = true
)
