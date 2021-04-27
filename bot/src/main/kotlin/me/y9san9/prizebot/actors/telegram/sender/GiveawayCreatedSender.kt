package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromUserLocalizedDIBotUpdate


object GiveawayCreatedSender {
    suspend fun <T> send(update: FromUserLocalizedDIBotUpdate<T>, giveaway: Giveaway) where
            T : GiveawaysStorage, T : LanguageCodesStorage {
        update.sendMessage(update.locale.giveawayCreated, replyMarkup = mainMarkup(update))
        GiveawaySender.send(update, giveaway, demo = true)
    }
}
