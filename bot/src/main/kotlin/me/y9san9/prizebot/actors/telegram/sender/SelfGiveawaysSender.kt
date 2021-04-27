package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.resources.content.noGiveawaysYetContent
import me.y9san9.prizebot.resources.content.selfGiveawaysContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromUserLocalizedDIBotUpdate


object SelfGiveawaysSender {
    suspend fun <T> send(event: FromUserLocalizedDIBotUpdate<T>) where
            T :  LanguageCodesStorage, T : GiveawaysStorage {
        val (entities, markup) = selfGiveawaysContent(event)
            ?: noGiveawaysYetContent(event)

        event.sendMessage(entities, replyMarkup = markup)
    }
}
