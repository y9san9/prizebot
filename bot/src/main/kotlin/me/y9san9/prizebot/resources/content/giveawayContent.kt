package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.resources.entities.giveawayEntities
import me.y9san9.prizebot.resources.markups.giveawayMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


suspend fun <TUpdate, TDI> extractGiveawayContent (
    update: TUpdate,
    splitter: String = "\\s+",
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?>? where
        TUpdate : HasTextUpdate, TUpdate : FromChatLocalizedDIBotUpdate<TDI>,
        TDI : GiveawaysStorage {
    val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return null

    return giveawayEntities(update, giveaway) to
        giveawayMarkup(giveaway, demo)
}

suspend fun giveawayContent (
    update: BotUpdate,
    giveaway: Giveaway,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?> {
    return giveawayEntities(update, giveaway) to
            giveawayMarkup(giveaway, demo)
}
