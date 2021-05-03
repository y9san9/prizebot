package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.images.Image
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendPhotoCached
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate


object StartSender {
    suspend fun <TUpdate, TDI> send(
        update: TUpdate
    ) where TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
            TUpdate : FromChatUpdate, TUpdate : FromUserUpdate,
            TDI : LanguageCodesStorage, TDI : GiveawaysStorage {
        update.sendPhotoCached(Image.socialPreview, update.locale.start, replyMarkup = mainMarkup(update))
    }
}
