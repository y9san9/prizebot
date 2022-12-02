package me.y9san9.prizebot.resources.markups

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
    val currentLocale = update.userId?.let { update.di.getLanguageCode(it) } ?: update.languageCode

    fun addCheckmarkIfSelected(locale: LocaleModel) =
        if (locale.code == currentLocale)
            "${locale.label} ${Emoji.CHECKMARK}"
        else locale.label

    val buttons = locales.chunked(size = 2).map { chunk ->
        chunk.map { locale ->
            CallbackDataInlineKeyboardButton(
                text = addCheckmarkIfSelected(locale),
                callbackData = "${CALLBACK_ACTION_SELECT_LOCALE}_${locale.code}"
            )
        }
    }

    return InlineKeyboardMarkup(buttons)
}
