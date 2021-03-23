package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.resources.entities.giveawayEntities
import me.y9san9.prizebot.resources.markups.giveawayMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


suspend fun <TUpdate, TDI> giveawayContent (
    update: TUpdate,
    splitter: String = "\\s+",
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?>? where
        TUpdate : HasTextUpdate, TUpdate : FromChatLocalizedDIBotUpdate<TDI>,
        TDI : GiveawaysStorage, TDI : ParticipantsStorage, TDI: LanguageCodesStorage {
    val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return null

    return giveawayEntities(update, giveaway) to
        giveawayMarkup(update, giveaway, demo)
}

suspend fun <T> giveawayContent (
    update: FromChatLocalizedDIBotUpdate<T>,
    giveaway: Giveaway,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?> where
        T : LanguageCodesStorage, T : ParticipantsStorage =
    // Zero won't be used if giveaway equals null
    giveawayContent(update, giveaway, getParticipantsOrZero(giveaway, update), demo)

private fun getParticipantsOrZero(giveaway: Giveaway?, update: DIUpdate<ParticipantsStorage>): Int {
    return update.di.getParticipantsCount (
        giveawayId = giveaway?.id ?: return 0
    )
}

suspend fun giveawayContent (
    update: PrizebotLocalizedBotUpdate,
    giveaway: Giveaway,
    participantsCount: Int,
    demo: Boolean = false
): Pair<TextSourcesList, InlineKeyboardMarkup?> {
    return giveawayEntities(update, giveaway) to
            giveawayMarkup(participantsCount, giveaway, demo)
}
