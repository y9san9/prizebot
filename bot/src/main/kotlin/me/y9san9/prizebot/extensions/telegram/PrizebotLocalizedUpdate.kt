package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.resources.locales.Locale


val PrizebotLocalizedUpdate.locale get() = Locale.with (
    language = di.getLanguageCode(chatId) ?: languageCode
)
