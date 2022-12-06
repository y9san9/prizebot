package me.y9san9.prizebot.resources.locales

import dev.inmo.micro_utils.language_codes.IetfLanguageCode
import me.y9san9.prizebot.resources.Emoji


data class LocaleModel(
    val label: String,
    val ietf: IetfLanguageCode,
    val locale: Locale
)

val locales = listOf(
    LocaleModel(
        label = "${Emoji.Flag.UK}${Emoji.Flag.US} English",
        ietf = IetfLanguageCode.English,
        locale = DefaultLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.GERMANY} Deutsch",
        ietf = IetfLanguageCode.German,
        locale = DeLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.ITALY} Italiano",
        ietf = IetfLanguageCode.Italian,
        locale = ItLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.UKRAINE} Українська",
        ietf = IetfLanguageCode.Ukrainian,
        locale = UkLocale,
    ),
    LocaleModel(
        label = "${Emoji.Flag.KAZAKHSTAN} Қазақ",
        ietf = IetfLanguageCode.Kazakh,
        locale = KkLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.POLAND} Polski",
        ietf = IetfLanguageCode.Polish,
        locale = PlLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.RUSSIA} Русский",
        ietf = IetfLanguageCode.Russian,
        locale = RuLocale
    ),
    LocaleModel(
        label = "${Emoji.Flag.BELARUS} Беларуская",
        ietf = IetfLanguageCode.Belarusian,
        locale = BeLocale
    )
)
