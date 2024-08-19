package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale

suspend fun noGiveawaysYetContent(event: PrizebotLocalizedUpdate): Pair<TextSourcesList, KeyboardMarkup?> =
    listOf(regular(event.getLocale().noGiveawaysYet)) to null
