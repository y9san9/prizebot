package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


object GiveawaySender {
    suspend fun <TUpdate, TDI> send(update: TUpdate) where
            TUpdate : HasTextUpdate, TUpdate : FromChatLocalizedDIBotUpdate<TDI>,
            TDI : ParticipantsStorage, TDI: GiveawaysStorage, TDI : LanguageCodesStorage {

        val (entities, markup) = giveawayContent(update) ?: return
        update.sendMessage(entities, replyMarkup = markup)
    }

    suspend fun send(update: PrizebotLocalizedBotUpdate, giveaway: Giveaway, participantsCount: Int, demo: Boolean = false) {
        val (entities, markup) = giveawayContent(update, giveaway, participantsCount, demo)
        update.sendMessage(entities, replyMarkup = markup)
    }
}
