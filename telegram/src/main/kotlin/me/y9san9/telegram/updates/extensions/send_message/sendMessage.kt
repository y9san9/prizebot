package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.CommonAbstracts.TextSource
import dev.inmo.tgbotapi.extensions.api.send.media.sendPhoto
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.requests.abstracts.toInputFile
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatBotUpdate
import java.io.File


suspend fun FromChatBotUpdate.sendMessage (
    text: String,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = bot.sendMessage (
    ChatId(chatId), text, parseMode,
    disableWebPagePreview, disableNotification, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)


suspend fun FromChatBotUpdate.sendMessage (
    entities: List<TextSource>,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = bot.sendMessage (
    ChatId(chatId), entities, disableWebPagePreview,
    disableNotification, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)
