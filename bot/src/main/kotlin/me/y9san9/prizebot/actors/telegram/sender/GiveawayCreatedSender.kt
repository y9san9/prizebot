package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


object GiveawayCreatedSender {
    suspend fun <T> send(update: T, giveaway: Giveaway) where
            T : FromChatLocalizedBotUpdate, T : DIUpdate<out GiveawaysStorage> {
        update.sendMessage(update.locale.giveawayCreated, replyMarkup = mainMarkup(update))
        GiveawaySender.send(update, giveaway, participantsCount = 0)
    }
}
