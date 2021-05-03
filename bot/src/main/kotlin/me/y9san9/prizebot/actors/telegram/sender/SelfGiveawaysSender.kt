package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.resources.content.noGiveawaysYetContent
import me.y9san9.prizebot.resources.content.selfGiveawaysContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate


object SelfGiveawaysSender {
    suspend fun <TUpdate, TDI> send (
        event: TUpdate
    ) where TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
            TUpdate : FromChatUpdate, TUpdate : FromUserUpdate,
            TDI :  LanguageCodesStorage, TDI : GiveawaysStorage {
        val (entities, markup) = selfGiveawaysContent(event)
            ?: noGiveawaysYetContent(event)

        event.sendMessage(entities, replyMarkup = markup)
    }
}
