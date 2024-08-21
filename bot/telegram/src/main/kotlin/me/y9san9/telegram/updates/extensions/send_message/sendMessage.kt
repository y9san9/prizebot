package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.ParseMode
import dev.inmo.tgbotapi.types.message.textsources.TextSource
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate


suspend fun <T> T.sendMessage (
    text: String,
    parseMode: ParseMode? = null,
    linkPreviewOptions: LinkPreviewOptions? = null,
    threadId: MessageThreadId? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyParameters: ReplyParameters? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendMessage(
    chatId = chatId.toChatId(),
    text = text,
    parseMode = parseMode,
    linkPreviewOptions = linkPreviewOptions,
    threadId = threadId,
    disableNotification = disableNotification,
    protectContent = protectContent,
    replyParameters = replyParameters,
    replyMarkup = replyMarkup
)


suspend fun <T> T.sendMessage (
    entities: List<TextSource>,
    linkPreviewOptions: LinkPreviewOptions? = null,
    threadId: MessageThreadId? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyParameters: ReplyParameters? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendMessage(
    chatId = chatId.toChatId(),
    entities = entities,
    linkPreviewOptions = linkPreviewOptions,
    threadId = threadId,
    disableNotification = disableNotification,
    protectContent = protectContent,
    replyParameters = replyParameters,
    replyMarkup = replyMarkup
)
