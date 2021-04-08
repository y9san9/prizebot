package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.resources.content.startContent
import me.y9san9.prizebot.resources.images.Image
import me.y9san9.telegram.updates.extensions.send_message.sendPhoto
import me.y9san9.telegram.updates.extensions.send_message.sendPhotoCached
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate


object StartSender {
    suspend fun <T> send(update: FromChatLocalizedDIBotUpdate<T>) where T : LanguageCodesStorage, T : GiveawaysStorage {
        val (text, markup) = startContent(update)
        update.sendPhotoCached(Image.socialPreview, text, replyMarkup = markup)
    }
}
