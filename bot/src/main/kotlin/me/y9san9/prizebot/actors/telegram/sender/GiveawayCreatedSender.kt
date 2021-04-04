package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate


object GiveawayCreatedSender {
    suspend fun <T> send(update: FromChatLocalizedDIBotUpdate<T>, giveaway: Giveaway) where
            T : GiveawaysStorage, T : LanguageCodesStorage {
        update.sendMessage(update.locale.giveawayCreated, replyMarkup = mainMarkup(update))
        GiveawaySender.send(update, giveaway, participantsCount = 0, demo = true)
    }
}
