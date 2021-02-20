package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.answers.answerCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.DataCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.InlineMessageIdCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.MessageDataCallbackQuery
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.update.CallbackQueryUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.AnswerableUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


class CallbackQueryUpdate <DI> (
    override val bot: TelegramBot,
    override val di: DI,
    private val query: CallbackQueryUpdate,
) : DIUpdate<DI>, FromChatLocalizedBotUpdate, HasTextUpdate, AnswerableUpdate {

    override val chatId = query.data.user.id.chatId
    override val languageCode = (query.data.user as? CommonUser)?.languageCode
    override val text = (query.data as? DataCallbackQuery)?.data

    val message = (query.data as? MessageCallbackQuery)?.message
    val inlineMessageId = (query.data as? InlineMessageIdCallbackQuery)?.inlineMessageId

    suspend fun answer (
        text: String? = null,
        showAlert: Boolean? = null,
        url: String? = null,
        cachedTimeSeconds: Int? = null
    ) = bot.answerCallbackQuery (
        query.data, text, showAlert, url, cachedTimeSeconds
    ).let { }

    override suspend fun answer() = answer(text = null)
}
