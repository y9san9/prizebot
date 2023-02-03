package me.y9san9.prizebot.resources.locales.ietf

import dev.inmo.micro_utils.language_codes.IetfLanguageCode

fun IetfLanguageCode.ignoreDialect(): IetfLanguageCode =
    IetfLanguageCode(code.split("-").first())
