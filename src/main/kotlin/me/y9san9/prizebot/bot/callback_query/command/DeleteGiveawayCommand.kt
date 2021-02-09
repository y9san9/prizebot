package me.y9san9.prizebot.bot.callback_query.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.bot.shortcuts.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.bot.shortcuts.asTextContentMessage
import me.y9san9.prizebot.bot.shortcuts.locale
import me.y9san9.prizebot.logic.actor.GiveawayFromCommandExtractor
import me.y9san9.telegram.updates.message.Command


object DeleteGiveawayCommand {
    suspend fun handle(query: PrizebotCallbackQueryUpdate, command: Command) {
        val giveaway = GiveawayFromCommandExtractor.extract(query.di, command) ?: return

        val message = query.message?.asTextContentMessage() ?: return
        val storage = query.di.giveawaysStorage
        val userId = query.from.id.chatId

        if(giveaway.ownerId != userId)
            return

        storage.deleteGiveaway(giveaway.id)

        query.bot.editMessageText(message, entities = query.locale.giveawayDeleted(giveaway.title))
        query.answer()
    }
}
