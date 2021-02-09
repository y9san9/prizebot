package me.y9san9.telegram.extensions

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.CommonRequestException
import dev.inmo.tgbotapi.bot.exceptions.MessageIsNotModifiedException
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.deleteMessage
import dev.inmo.tgbotapi.extensions.api.edit.ReplyMarkup.editMessageReplyMarkup
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.utils.asPrivateChat
import dev.inmo.tgbotapi.extensions.utils.formatting.toMarkdownV2Texts
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.MessageEntity.textsources.*
import dev.inmo.tgbotapi.types.ParseMode.MarkdownV2
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.content.TextContent


suspend fun TelegramBot.deleteMessageSafe (
    message: Message
) = try {
    deleteMessage(message)
    true
} catch (_: CommonRequestException) {
    false
}

@Suppress("EXPERIMENTAL_API_USAGE")
val TelegramBot.userLinkProvider: suspend (Long) -> TextMentionTextSource? get() = local@ {
    val user = getChat(ChatId(it)).asPrivateChat() ?: return@local null
    mention(text = "${user.firstName} ${user.lastName}", user = CommonUser(id = user.id, firstName = ""))
}
