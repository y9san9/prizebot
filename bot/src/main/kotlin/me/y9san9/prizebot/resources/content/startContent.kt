package me.y9san9.prizebot.resources.content

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


fun <T> startContent(update: T) where T : FromChatLocalizedUpdate, T : DIUpdate<out GiveawaysStorage> =
    update.locale.start to mainMarkup(update)
