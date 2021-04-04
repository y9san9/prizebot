package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.extensions.utils.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.extensions.telegram.locale


fun confirmationMarkup (
    update: PrizebotLocalizedUpdate,
    confirmationAction: String,
    cancelAction: String
) = InlineKeyboardMarkup (
    CallbackDataInlineKeyboardButton (
        text = update.locale.cancel,
        callbackData = cancelAction
    ),
    CallbackDataInlineKeyboardButton (
        text = update.locale.confirm,
        callbackData = confirmationAction
    )
)
