package me.y9san9.telegram.extensions

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.MessageIsNotModifiedException
import dev.inmo.tgbotapi.extensions.api.edit.ReplyMarkup.editMessageReplyMarkup
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.extensions.utils.formatting.toMarkdownV2Texts
import dev.inmo.tgbotapi.types.InlineMessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.*
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import me.y9san9.telegram.Content


suspend fun TelegramBot.editMessageTextSafe (
    inlineMessageId: InlineMessageIdentifier,
    content: Content<InlineKeyboardMarkup>,
    disableWebPagePreview: Boolean? = null
) = try {
    editMessageText(inlineMessageId, content.entities, disableWebPagePreview, content.replyMarkup)
} catch (_: MessageIsNotModifiedException) {}

suspend fun TelegramBot.editMessageTextSafe (
    message: ContentMessage<TextContent>,
    content: Content<InlineKeyboardMarkup>,
    disableWebPagePreview: Boolean? = null
) = try {
    editMessageText (
        message, content.entities, disableWebPagePreview, content.replyMarkup
    )
} catch (_: MessageIsNotModifiedException) {}

suspend fun TelegramBot.editMessageReplyMarkupSafe (
    inlineMessageId: InlineMessageIdentifier,
    replyMarkup: InlineKeyboardMarkup? = null
) = try {
    editMessageReplyMarkup(inlineMessageId, replyMarkup)
} catch (_: MessageIsNotModifiedException) {}

suspend fun TelegramBot.editMessageText (
    message: ContentMessage<TextContent>, content: Content<InlineKeyboardMarkup>,
    disableWebPagePreview: Boolean? = null
) = editMessageText(message, content.entities, disableWebPagePreview, content.replyMarkup)
