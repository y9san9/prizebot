package me.y9san9.prizebot.handlers.inline_queries.command

import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.mapper.GiveawayToResultArticleMapper
import me.y9san9.prizebot.models.telegram.PrizebotInlineQueryUpdate


object SendGiveawayCommand {
    suspend fun handle(update: PrizebotInlineQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_")
            ?.takeIf { it.ownerId == update.chatId } ?: return

        update.answer (
            GiveawayToResultArticleMapper.map(queryId = "1", update, giveaway),
            isPersonal = true
        )
    }
}
