package me.y9san9.telegram.updates.inline_query

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.answers.answerInlineQuery
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.InlineQueries.InlineQueryResult.abstracts.InlineQueryResult
import dev.inmo.tgbotapi.types.update.InlineQueryUpdate


class BotInlineQueryUpdate <DI> (
    val bot: TelegramBot,
    val di: DI,
    val update: InlineQueryUpdate
) {
    val from = update.data.from
    val languageCode = (update.data.from as CommonUser).languageCode

    suspend fun answer (
        result: InlineQueryResult,
        cachedTime: Int? = null,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null
    ) = answer(listOf(result), cachedTime, isPersonal, nextOffset, switchPmText, switchPmParameter)

    suspend fun answer (
        results: List<InlineQueryResult> = emptyList(),
        cachedTime: Int? = null,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null
    ) = bot.answerInlineQuery(update.data, results, cachedTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
}
