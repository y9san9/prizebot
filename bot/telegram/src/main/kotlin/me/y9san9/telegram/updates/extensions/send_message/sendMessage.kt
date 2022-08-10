package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.ParseMode
import dev.inmo.tgbotapi.types.message.textsources.TextSource
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate


suspend fun <T> T.sendMessage (
    text: String,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendMessage(
    ChatId(chatId), text, parseMode, disableWebPagePreview,
    disableNotification, protectContent, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)


suspend fun <T> T.sendMessage (
    entities: List<TextSource>,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendMessage(
    ChatId(chatId), entities, disableWebPagePreview,
    disableNotification, protectContent, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)
