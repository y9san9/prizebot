package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.resources.entities.giveawayEntities
import me.y9san9.prizebot.resources.markups.giveawayMarkup
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


suspend fun <TUpdate, TDI> giveawayContent (
    update: TUpdate,
    splitter: String = "\\s+",
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup>? where
        TUpdate : HasTextUpdate, TUpdate : DIUpdate<out TDI>, TUpdate : BotUpdate,
        TDI : GiveawaysStorage, TDI : ParticipantsStorage {
    val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return null

    return giveawayEntities(update, giveaway) to
        giveawayMarkup(update, giveaway, demo)
}

suspend fun <T> giveawayContent (
    update: T,
    giveaway: Giveaway,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup> where
        T : BotUpdate, T : DIUpdate<out ParticipantsStorage> =
    giveawayContent(update, giveaway, update.di.getParticipantsCount(giveaway.id), demo)

suspend fun giveawayContent (
    update: BotUpdate,
    giveaway: Giveaway,
    participantsCount: Int,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup> {
    return giveawayEntities(update, giveaway) to
            giveawayMarkup(participantsCount, giveaway, demo)
}
