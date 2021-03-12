package me.y9san9.prizebot.actors.telegram.extractor

import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.telegram.updates.extensions.command.command
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


object GiveawayFromCommandExtractor {
    fun <T> extract(update: T, splitter: String = "\\s+"): Giveaway? where
            T : HasTextUpdate, T : DIUpdate<GiveawaysStorage> {
        val giveawayId = update.command(splitter)?.args?.getOrNull(0)?.toLongOrNull() ?: return null
        return update.di.getGiveawayById(giveawayId)
    }
}
