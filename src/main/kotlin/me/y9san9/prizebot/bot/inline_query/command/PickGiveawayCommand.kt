package me.y9san9.prizebot.bot.inline_query.command

import me.y9san9.prizebot.bot.shortcuts.PrizebotInlineQueryUpdate
import me.y9san9.prizebot.bot.shortcuts.locale
import me.y9san9.prizebot.bot.shortcuts.toInlineResultArticle
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.extensions.userLinkProvider


object PickGiveawayCommand {
    suspend fun handle(query: PrizebotInlineQueryUpdate) {
        val storage = query.di.giveawaysStorage
        val userId = query.from.id.chatId
        val offset = query.update.data.offset.toLongOrNull() ?: 0

        val giveaways = storage.getUserGiveaways(userId, count = 10, offset)

        val results = giveaways.mapIndexed { i, item ->
            item.toInlineResultArticle (
                queryId = "${i + offset}", query.di.participantsStorage, query.bot.userLinkProvider
            )
        }

        if(results.isEmpty())
            if(offset == 0L) query.answer (
                switchPmText = Locale.with(query.languageCode).switchPmNoGiveawaysYet,
                switchPmParameter = "giveaway",
                cachedTime = 0,
                isPersonal = true
            )  else query.answer(cachedTime = 0, isPersonal = true)
        else query.answer (
            results,
            nextOffset = "${offset + 10}",
            cachedTime = 0,
            isPersonal = true
        )
    }
}
