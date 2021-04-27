package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.hierarchies.FromUserLocalizedDIBotUpdate


fun <T> mainMarkup(update: FromUserLocalizedDIBotUpdate<T>): KeyboardMarkup where
        T : GiveawaysStorage, T : LanguageCodesStorage {
    val storage = update.di
    val userId = update.userId
    val locale = update.locale

    val buttons = listOf (
        listOf (
            SimpleKeyboardButton(locale.helpKeyboard),
            SimpleKeyboardButton(locale.giveawayKeyboard)
        )
    )

    val giveawaysButton = listOf(SimpleKeyboardButton(locale.selfGiveawaysKeyboard))
    val hasSelfGiveawaysButton = storage.getUserGiveaways(userId).isNotEmpty()

    return ReplyKeyboardMarkup (
        resizeKeyboard = true,
        keyboard = if(hasSelfGiveawaysButton) buttons + listOf(giveawaysButton) else buttons
    )
}
