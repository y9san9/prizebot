package me.y9san9.prizebot.resources.locales.ietf

import dev.inmo.micro_utils.language_codes.IetfLang

fun IetfLang.ignoreDialect(): IetfLang =
    IetfLang(code.split("-").first())
