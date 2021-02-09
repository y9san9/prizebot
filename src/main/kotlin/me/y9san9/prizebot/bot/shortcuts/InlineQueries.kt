package me.y9san9.prizebot.bot.shortcuts

import dev.inmo.tgbotapi.types.InlineQueries.InlineQueryResult.InlineQueryResultArticle
import dev.inmo.tgbotapi.types.InlineQueries.InputMessageContent.InputTextMessageContent
import dev.inmo.tgbotapi.types.MessageEntity.textsources.TextMentionTextSource
import me.y9san9.prizebot.logic.database.ParticipantsStorage
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.prizebot.models.locale


suspend fun Giveaway.toInlineResultArticle (
    queryId: String,
    storage: ParticipantsStorage,
    userLinkProvider: suspend (Long) -> TextMentionTextSource?
) = InlineQueryResultArticle (
    id = queryId,
    title = title,
    description = locale.participateButton(participateButton),
    inputMessageContent = toInputMessageContent(userLinkProvider),
    replyMarkup = giveawayMarkup (
        giveaway = this,
        storage.getParticipantsCount(id),
    ),
)

suspend fun Giveaway.toInputMessageContent(userLinkProvider: suspend (Long) -> TextMentionTextSource?) =
    InputTextMessageContent (
        entities = giveawayEntities(giveaway = this, userLinkProvider),
    )
