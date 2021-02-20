package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatBotUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


object GiveawaySender {
    suspend fun <TUpdate, TDI> send(update: TUpdate) where
            TUpdate : FromChatLocalizedBotUpdate, TUpdate : HasTextUpdate,
            TUpdate : DIUpdate<TDI>, TDI : ParticipantsStorage, TDI: GiveawaysStorage {

        val (entities, markup) = giveawayContent(update) ?: return
        update.sendMessage(entities, replyMarkup = markup)
    }

    suspend fun send(update: FromChatBotUpdate, giveaway: Giveaway, participantsCount: Int) {
        val (entities, markup) = giveawayContent(update, giveaway, participantsCount)
        update.sendMessage(entities, replyMarkup = markup)
    }
}
