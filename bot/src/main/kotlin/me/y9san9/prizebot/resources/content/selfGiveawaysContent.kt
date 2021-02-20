package me.y9san9.prizebot.resources.content

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.resources.entities.selfGiveawaysEntities
import me.y9san9.prizebot.resources.markups.selfGiveawaysMarkup
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


fun <T> selfGiveawaysContent (
    update: T,
    offset: Long = 0,
    count: Int = 5
): Pair<TextSourcesList, InlineKeyboardMarkup>? where T : DIUpdate<out GiveawaysStorage>, T : FromChatLocalizedBotUpdate {
    val storage = update.di
    val userId = update.chatId
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
