@file:Suppress("EXPERIMENTAL_API_USAGE")

package me.y9san9.telegram.updates.message

import dev.inmo.tgbotapi.CommonAbstracts.TextSource
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.utils.asCommonUser
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.abstracts.FromUserMessage
import dev.inmo.tgbotapi.types.message.abstracts.PrivateMessage


/**
 * A wrapper around bot update with availability to answer on it
 */
class BotMessageUpdate <DI> (
    val bot: TelegramBot,
    val di: DI,
    val update: PrivateMessage<*>,
) {
    val chatId: Long = update.chat.id.chatId
    val languageCode: String? = (update as? FromUserMessage)?.user?.asCommonUser()?.languageCode

    suspend fun sendMessage (
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean = false,
        replyToMessageId: MessageIdentifier? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: KeyboardMarkup? = null
    ) = bot.sendMessage (
        update.chat,
        text,
        parseMode,
        disableWebPagePreview,
        disableNotification,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )

    suspend fun sendMessage (
        entities: List<TextSource>,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean = false,
        replyToMessageId: MessageIdentifier? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: KeyboardMarkup? = null
    ) = bot.sendMessage (
        update.chat,
        entities,
        disableWebPagePreview,
        disableNotification,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )

}
