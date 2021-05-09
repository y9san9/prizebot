package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.RequestException
import dev.inmo.tgbotapi.extensions.api.answers.answerCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.DataCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.InlineMessageIdCallbackQuery
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.update.CallbackQueryUpdate
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.AnswerableUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


class CallbackQueryUpdate <DI> (
    override val bot: TelegramBot,
    override val di: DI,
    private val query: CallbackQueryUpdate,
) : PossiblyFromUserLocalizedDIBotUpdate<DI>, HasTextUpdate, AnswerableUpdate, FromUserUpdate {

    override val userId = query.data.user.id.chatId
    override val languageCode = (query.data.user as? CommonUser)?.languageCode
    override val text = (query.data as? DataCallbackQuery)?.data

    val message = (query.data as? MessageCallbackQuery)?.message
    val inlineMessageId = (query.data as? InlineMessageIdCallbackQuery)?.inlineMessageId

    suspend fun answer (
        text: String? = null,
        showAlert: Boolean? = null,
        url: String? = null,
        cachedTimeSeconds: Int? = null
    ) = try {
        bot.answerCallbackQuery (
            query.data, text, showAlert, url, cachedTimeSeconds
        ).let { }
    } catch (_: RequestException) {}

    override suspend fun answer() = answer(text = null)
}
