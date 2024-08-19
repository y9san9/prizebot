package me.y9san9.prizebot.handlers.inline_queries.command

import me.y9san9.prizebot.actors.telegram.mapper.GiveawayToResultArticleMapper
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.extensions.telegram.PrizebotInlineQueryUpdate


object PickGiveawayCommand {
    suspend fun handle(update: PrizebotInlineQueryUpdate) {
        val storage = update.di
        val userId = update.userId
        val offset = update.offset.toLongOrNull() ?: 0

        val giveaways = storage.getUserGiveaways(userId, count = 10, offset)

        val results = giveaways.mapIndexed { i, giveaway ->
            GiveawayToResultArticleMapper.map (
                resultId = "${i + offset}",
                update, giveaway
            )
        }

        if(results.isEmpty())
            if(offset == 0L) update.answer (
                switchPmText = update.getLocale().switchPmNoGiveawaysYet,
                switchPmParameter = "giveaway",
                cachedTime = 0,
                isPersonal = true
            )  else update.answer(cachedTime = 0, isPersonal = true)
        else update.answer (
            results,
            nextOffset = "${offset + 10}",
            cachedTime = 0,
            isPersonal = true
        )
    }
}
