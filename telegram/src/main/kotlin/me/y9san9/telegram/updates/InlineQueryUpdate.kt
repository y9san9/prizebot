package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.answers.answerInlineQuery
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.InlineQueries.InlineQueryResult.abstracts.InlineQueryResult
import dev.inmo.tgbotapi.types.update.InlineQueryUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.AnswerableUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


class InlineQueryUpdate <DI> (
    override val bot: TelegramBot,
    override val di: DI,
    private val query: InlineQueryUpdate,
) : DIUpdate<DI>, FromChatLocalizedBotUpdate, HasTextUpdate, AnswerableUpdate {

    override val chatId = query.data.from.id.chatId
    override val languageCode = (query.data.from as? CommonUser)?.languageCode
    override val text = query.data.query

    val offset = query.data.offset

    suspend fun answer (
        results: List<InlineQueryResult> = emptyList(),
        cachedTime: Int? = null,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null
    ) = bot.answerInlineQuery (
        query.data, results, cachedTime, isPersonal,
        nextOffset, switchPmText, switchPmParameter
    ).let { }

    suspend fun answer (
        result: InlineQueryResult,
        cachedTime: Int? = null,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null
    ) = answer(listOf(result), cachedTime, isPersonal, nextOffset, switchPmText, switchPmParameter)

    override suspend fun answer() = answer(emptyList())
}
