package me.y9san9.prizebot.resources.locales

import me.y9san9.prizebot.resources.Emoji


data class LocaleModel (
    val label: String,
    val code: String,
    val locale: Locale
)

val locales = listOf (
    LocaleModel (
        label = "${Emoji.ENGLISH} English",
        code = "en",
        locale = DefaultLocale
    ),
    LocaleModel (
        label = "${Emoji.RUSSIAN} Русский",
        code = "ru",
        locale = RuLocale
    )
)
