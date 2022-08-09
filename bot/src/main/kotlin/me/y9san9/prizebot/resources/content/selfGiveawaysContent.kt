package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.resources.entities.selfGiveawaysEntities
import me.y9san9.prizebot.resources.markups.selfGiveawaysMarkup
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate


fun <TUpdate, TDI> selfGiveawaysContent (
    update: TUpdate,
    offset: Long = 0,
    count: Int = 5
): Pair<TextSourcesList, InlineKeyboardMarkup>? where
        TUpdate : PossiblyFromUserLocalizedDIBotUpdate<TDI>,
        TUpdate : FromUserUpdate,
        TDI : GiveawaysStorage, TDI : LanguageCodesStorage {
    val storage = update.di
    val userId = update.userId
    val languageCode = update.languageCode

    val giveawaysPlusOne = storage.getUserGiveaways (
        ownerId = userId,
        count = count + 1,
        offset = offset
    )
    val hasNext = giveawaysPlusOne.size == count + 1
    val giveaways = giveawaysPlusOne.take(n = count)

    if(giveaways.isEmpty())
        return null

    val giveawaysTitles = giveaways.map(Giveaway::title)

    return selfGiveawaysEntities (
        languageCode,
        giveawaysTitles,
        offset = offset
    ) to selfGiveawaysMarkup (
        offset = offset,
        giveaways = giveaways,
        hasNext = hasNext
    )
}
