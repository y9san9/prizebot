package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.extensions.telegram.locale


fun timezoneKeyboard(update: PrizebotLocalizedUpdate) = ReplyKeyboardMarkup (
    keyboard = listOf (
        listOf(SimpleKeyboardButton(update.locale.customTimeOffset)),
        listOf (
            SimpleKeyboardButton(update.locale.GMT),
            SimpleKeyboardButton(update.locale.`UTC-4`),
            SimpleKeyboardButton(update.locale.UTC1),
        ),
        listOf (
            SimpleKeyboardButton(update.locale.UTC2),
            SimpleKeyboardButton(update.locale.UTC3),
            SimpleKeyboardButton(update.locale.UTC5_30)
        ),
        listOf (
            SimpleKeyboardButton(update.locale.UTC8),
            SimpleKeyboardButton(update.locale.UTC9)
        )
    ),
    resizeKeyboard = true,
    oneTimeKeyboard = true
)
