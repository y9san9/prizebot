package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale


fun conditionsMarkup(update: PrizebotLocalizedBotUpdate) = ReplyKeyboardMarkup (
    keyboard = listOf (
        listOf (
            SimpleKeyboardButton(update.locale.channelSubscription)
        ),
//        listOf (
//            SimpleKeyboardButton(update.locale.invitations)
//        )
    ),
    resizeKeyboard = true
)
