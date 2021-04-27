package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.extensions.utils.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.resources.CALLBACK_ACTION_SELECT_LOCALE
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.locales.LocaleModel
import me.y9san9.prizebot.resources.locales.locales


fun selectLocaleMarkup (
    update: PrizebotLocalizedUpdate
): InlineKeyboardMarkup {
    val currentLocale = update.di.getLanguageCode(update.userId) ?: update.languageCode

    fun addCheckmarkIfSelected(locale: LocaleModel) =
        if (locale.code == currentLocale)
            "${locale.label} ${Emoji.CHECKMARK}"
        else locale.label

    val buttons = locales.map {
        CallbackDataInlineKeyboardButton(
            addCheckmarkIfSelected(it),
            "${CALLBACK_ACTION_SELECT_LOCALE}_${it.code}"
        )
    }.toTypedArray()

    return InlineKeyboardMarkup(*buttons)
}
