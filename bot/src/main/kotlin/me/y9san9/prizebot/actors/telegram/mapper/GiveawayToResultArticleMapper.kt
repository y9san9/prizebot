package me.y9san9.prizebot.actors.telegram.mapper

import dev.inmo.tgbotapi.types.InlineQueries.InlineQueryResult.InlineQueryResultArticle
import dev.inmo.tgbotapi.types.InlineQueries.InputMessageContent.InputTextMessageContent
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.extensions.telegram.PrizebotInlineQueryUpdate
import me.y9san9.prizebot.resources.entities.giveawayEntities
import me.y9san9.prizebot.resources.markups.giveawayMarkup


object GiveawayToResultArticleMapper {
    suspend fun map (
        resultId: String, update: PrizebotInlineQueryUpdate, giveaway: Giveaway
    ) = InlineQueryResultArticle (
        id = resultId,
        title = giveaway.title,
        description = update.getLocale().participateText(giveaway.participateText),
        inputMessageContent = InputTextMessageContent(giveawayEntities(update.di, giveaway)),
        replyMarkup = giveawayMarkup(giveaway)
    )
}
