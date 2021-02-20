package me.y9san9.prizebot.handlers.callback_queries.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.models.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.telegram.utils.asTextContentMessage


object DeleteGiveawayCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_") ?: return

        val message = update.message?.asTextContentMessage() ?: return
        val storage = update.di as GiveawaysStorage
        val userId = update.chatId

        if(giveaway.ownerId != userId)
            return

        storage.deleteGiveaway(giveaway.id)

        update.bot.editMessageText (
            message, entities = update.locale.giveawayDeleted(giveaway.title)
        )
        update.answer()
    }
}
