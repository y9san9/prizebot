package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale

suspend fun timezoneKeyboard(update: PrizebotLocalizedUpdate) = ReplyKeyboardMarkup (
    keyboard = listOf (
        listOf(SimpleKeyboardButton(update.getLocale().customTimeOffset)),
        listOf (
            SimpleKeyboardButton(update.getLocale().GMT),
            SimpleKeyboardButton(update.getLocale().`UTC-4`),
            SimpleKeyboardButton(update.getLocale().UTC1),
        ),
        listOf (
            SimpleKeyboardButton(update.getLocale().UTC2),
            SimpleKeyboardButton(update.getLocale().UTC3),
            SimpleKeyboardButton(update.getLocale().UTC5_30)
        ),
        listOf (
            SimpleKeyboardButton(update.getLocale().UTC8),
            SimpleKeyboardButton(update.getLocale().UTC9)
        )
    ),
    resizeKeyboard = true,
    oneTimeKeyboard = true
)
