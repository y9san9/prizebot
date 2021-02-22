package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.resources.content.startContent
import me.y9san9.prizebot.resources.images.Image
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.extensions.send_message.sendPhoto
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


object StartSender {
    suspend fun <T> send(update: T) where T : FromChatLocalizedBotUpdate, T : DIUpdate<out GiveawaysStorage> {
        val (text, markup) = startContent(update)
        update.sendPhoto(Image.socialPreview, text, replyMarkup = markup)
    }
}
