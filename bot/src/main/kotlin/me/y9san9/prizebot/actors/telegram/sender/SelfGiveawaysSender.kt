package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.resources.content.noGiveawaysYetContent
import me.y9san9.prizebot.resources.content.selfGiveawaysContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate


object SelfGiveawaysSender {
    suspend fun <T> send(event: FromChatLocalizedDIBotUpdate<T>) where
            T :  LanguageCodesStorage, T : GiveawaysStorage {
        val (entities, markup) = selfGiveawaysContent(event)
            ?: noGiveawaysYetContent(event)

        event.sendMessage(entities, replyMarkup = markup)
    }
}
