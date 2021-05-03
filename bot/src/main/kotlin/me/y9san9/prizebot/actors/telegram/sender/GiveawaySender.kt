package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.prizebot.resources.content.extractGiveawayContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


object GiveawaySender {
    suspend fun <TUpdate, TDI> send(update: TUpdate) where
            TUpdate : HasTextUpdate, TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
            TUpdate : FromChatUpdate,
            TDI: GiveawaysStorage, TDI : LanguageCodesStorage, TDI : UserTitlesStorage {

        val (entities, markup) = extractGiveawayContent(update, update.di) ?: return
        update.sendMessage(entities, replyMarkup = markup)
    }

    suspend fun <T> send (
        update: T,
        userTitlesStorage: UserTitlesStorage,
        giveaway: Giveaway,
        demo: Boolean = false
    ) where T : BotUpdate, T : FromChatUpdate {
        val (entities, markup) = giveawayContent(userTitlesStorage, giveaway, demo)
        update.sendMessage(entities, replyMarkup = markup)
    }
}
