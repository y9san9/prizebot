package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.primitives.LocalizedUpdate


fun noGiveawaysYetContent(event: PrizebotLocalizedUpdate): Pair<TextSourcesList, KeyboardMarkup?> =
    listOf(regular(event.locale.noGiveawaysYet)) to null
