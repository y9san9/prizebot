package me.y9san9.prizebot.resources.content

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate


fun <T> startContent(update: FromChatLocalizedDIBotUpdate<T>) where T : LanguageCodesStorage, T : GiveawaysStorage =
    update.locale.start to mainMarkup(update)
