package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.CommonAbstracts.TextSource
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import me.y9san9.telegram.updates.hierarchies.FromUserBotUpdate


suspend fun FromUserBotUpdate.sendMessage (
    text: String,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = bot.sendMessage (
    ChatId(userId), text, parseMode,
    disableWebPagePreview, disableNotification, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)


suspend fun FromUserBotUpdate.sendMessage (
    entities: List<TextSource>,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = bot.sendMessage (
    ChatId(userId), entities, disableWebPagePreview,
    disableNotification, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)
