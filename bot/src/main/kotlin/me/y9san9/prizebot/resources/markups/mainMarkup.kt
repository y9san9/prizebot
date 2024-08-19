package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate


suspend fun <TUpdate, TDI> mainMarkup(update: TUpdate): KeyboardMarkup where
        TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
        TUpdate : FromUserUpdate,
        TDI : GiveawaysStorage, TDI : LanguageCodesStorage {
    val storage = update.di
    val userId = update.userId
    val locale = update.getLocale()

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
