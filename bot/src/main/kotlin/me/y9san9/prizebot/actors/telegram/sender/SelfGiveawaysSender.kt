package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.resources.content.noGiveawaysYetContent
import me.y9san9.prizebot.resources.content.selfGiveawaysContent
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


object SelfGiveawaysSender {
    suspend fun <T> send(event: T) where T : FromChatLocalizedBotUpdate, T : DIUpdate<out GiveawaysStorage> {
        val (entities, markup) = selfGiveawaysContent(event)
            ?: noGiveawaysYetContent(event)

        event.sendMessage(entities, replyMarkup = markup)
    }
}
