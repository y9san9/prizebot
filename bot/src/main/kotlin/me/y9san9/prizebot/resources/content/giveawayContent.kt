package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.resources.entities.giveawayEntities
import me.y9san9.prizebot.resources.markups.giveawayMarkup
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


suspend fun <TUpdate, TDI> extractGiveawayContent (
    update: TUpdate,
    userTitlesStorage: UserTitlesStorage,
    splitter: String = "\\s+",
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?>? where
        TUpdate : HasTextUpdate, TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
        TDI : GiveawaysStorage {
    val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return null

    return giveawayEntities(userTitlesStorage, giveaway) to
        giveawayMarkup(giveaway, demo)
}

suspend fun giveawayContent (
    userTitlesStorage: UserTitlesStorage,
    giveaway: Giveaway,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?> {
    return giveawayEntities(userTitlesStorage, giveaway) to
            giveawayMarkup(giveaway, demo)
}
