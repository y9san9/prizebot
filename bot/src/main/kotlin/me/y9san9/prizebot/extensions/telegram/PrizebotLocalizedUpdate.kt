package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.resources.locales.Locale


suspend fun PrizebotLocalizedUpdate.getLocale() = Locale.with(
    language = userId?.let { di.getLanguageCode(it) } ?: languageCode
)
