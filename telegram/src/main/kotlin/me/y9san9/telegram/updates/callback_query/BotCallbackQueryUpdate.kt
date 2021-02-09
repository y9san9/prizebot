package me.y9san9.telegram.updates.callback_query

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.answers.answerCallbackQuery
import dev.inmo.tgbotapi.extensions.utils.asInlineMessageIdDataCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.DataCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.InlineMessageIdCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.update.CallbackQueryUpdate


class BotCallbackQueryUpdate <DI> (
    val bot: TelegramBot,
    val di: DI,
    val update: CallbackQueryUpdate
) {
    val from = update.data.user
    val languageCode = (update.data.user as CommonUser).languageCode

    @Suppress("MemberVisibilityCanBePrivate")
    val inlineMessageId = (update.data as? InlineMessageIdCallbackQuery)?.inlineMessageId
    fun requireInlineMessageId() = inlineMessageId ?: error("Query is not inline")

    val data = (update.data as? DataCallbackQuery)?.data

    val message = (update.data as? MessageCallbackQuery)?.message
    fun requireMessage() = message ?: error("Query is not message")

    suspend fun answer (
        text: String? = null,
        showAlert: Boolean? = null,
        url: String? = null,
        cachedTimeSeconds: Int? = null
    ) = bot.answerCallbackQuery(update.data, text, showAlert, url, cachedTimeSeconds).let { }
}
