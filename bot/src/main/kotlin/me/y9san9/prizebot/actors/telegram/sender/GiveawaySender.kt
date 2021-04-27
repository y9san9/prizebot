package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.prizebot.resources.content.extractGiveawayContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


object GiveawaySender {
    suspend fun <TUpdate, TDI> send(update: TUpdate) where
            TUpdate : HasTextUpdate, TUpdate : FromUserLocalizedDIBotUpdate<TDI>,
            TDI: GiveawaysStorage, TDI : LanguageCodesStorage {

        val (entities, markup) = extractGiveawayContent(update) ?: return
        update.sendMessage(entities, replyMarkup = markup)
    }

    suspend fun send(update: PrizebotLocalizedBotUpdate, giveaway: Giveaway, demo: Boolean = false) {
        val (entities, markup) = giveawayContent(update, giveaway, demo)
        update.sendMessage(entities, replyMarkup = markup)
    }
}
