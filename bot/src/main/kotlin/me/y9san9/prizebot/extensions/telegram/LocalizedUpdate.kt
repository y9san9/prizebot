package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.primitives.LocalizedUpdate


val LocalizedUpdate.locale get() = Locale.with(languageCode)
