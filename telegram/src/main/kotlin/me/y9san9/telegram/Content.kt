package me.y9san9.telegram

import dev.inmo.tgbotapi.CommonAbstracts.TextSource
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup


typealias ContentAnyMarkup = Content<KeyboardMarkup>

data class Content <Markup : KeyboardMarkup> (
    val entities: List<TextSource>,
    val replyMarkup: Markup
)

