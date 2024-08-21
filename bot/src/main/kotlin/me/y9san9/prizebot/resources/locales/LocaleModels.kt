package me.y9san9.prizebot.resources.locales

import dev.inmo.micro_utils.language_codes.IetfLang
import me.y9san9.prizebot.resources.Emoji


data class LocaleModel(
    val label: String,
    val ietf: IetfLang,
    val locale: Locale
)

val locales = listOf(
    LocaleModel(
        label = "${Emoji.Flag.UK}${Emoji.Flag.US} English",
        ietf = IetfLang.English,
        locale = DefaultLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.GERMANY} Deutsch",
        ietf = IetfLang.German,
        locale = DeLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.ITALY} Italiano",
        ietf = IetfLang.Italian,
        locale = ItLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.UKRAINE} Українська",
        ietf = IetfLang.Ukrainian,
        locale = UkLocale,
    ),
    LocaleModel(
        label = "${Emoji.Flag.KAZAKHSTAN} Қазақ",
        ietf = IetfLang.Kazakh,
        locale = KkLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.POLAND} Polski",
        ietf = IetfLang.Polish,
        locale = PlLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.RUSSIA} Русский",
        ietf = IetfLang.Russian,
        locale = RuLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.BELARUS} Беларуская",
        ietf = IetfLang.Belarusian,
        locale = BeLocale
    )
)
