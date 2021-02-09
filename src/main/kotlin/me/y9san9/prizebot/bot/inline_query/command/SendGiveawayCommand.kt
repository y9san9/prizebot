package me.y9san9.prizebot.bot.inline_query.command

import me.y9san9.prizebot.bot.shortcuts.PrizebotInlineQueryUpdate
import me.y9san9.prizebot.bot.shortcuts.locale
import me.y9san9.prizebot.bot.shortcuts.toInlineResultArticle
import me.y9san9.prizebot.logic.actor.GiveawayFromCommandExtractor
import me.y9san9.telegram.updates.message.Command
import me.y9san9.telegram.extensions.userLinkProvider


object SendGiveawayCommand {
    suspend fun handle(query: PrizebotInlineQueryUpdate, command: Command) {
        val giveaway = GiveawayFromCommandExtractor.extract(query.di, command)
            ?.takeIf { it.ownerId == query.from.id.chatId } ?: return

        query.answer(giveaway.toInlineResultArticle (
            queryId = "1",
            query.di.participantsStorage,
            query.bot.userLinkProvider
        ), isPersonal = true)
    }
}
