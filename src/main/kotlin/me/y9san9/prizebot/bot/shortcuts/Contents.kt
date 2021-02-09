package me.y9san9.prizebot.bot.shortcuts

import dev.inmo.tgbotapi.types.MessageEntity.textsources.MentionTextSource
import dev.inmo.tgbotapi.types.MessageEntity.textsources.TextLinkTextSource
import dev.inmo.tgbotapi.types.MessageEntity.textsources.TextMentionTextSource
import dev.inmo.tgbotapi.types.MessageEntity.textsources.URLTextSource
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.logic.database.GiveawaysStorage
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.Content


fun selfGiveawaysContent (
    userId: Long,
    languageCode: String?,
    storage: GiveawaysStorage,
    offset: Long = 0,
    count: Int = 5
): Content<InlineKeyboardMarkup>? {
    val giveawaysPlusOne = storage.getUserGiveaways (
        ownerId = userId,
        count = count + 1,
        offset = offset
    )
    val hasNext = giveawaysPlusOne.size == count + 1
    val giveaways = giveawaysPlusOne.take(n = count)

    if(giveaways.isEmpty())
        return null

    require(giveaways.isNotEmpty()) { "At least one giveaway should be present" }

    val giveawaysTitles = giveaways.map(Giveaway::title)

    return Content (
        entities = selfGiveawaysMessage (
            languageCode,
            giveawaysTitles,
            offset = offset
        ),
        replyMarkup = selfGiveawaysMarkup (
            offset = offset,
            giveaways = giveaways,
            hasNext = hasNext
        )
    )
}

suspend fun giveawayContent (
    giveaway: Giveaway,
    participantsCount: Int,
    userLinkProvider: suspend (Long) -> TextMentionTextSource?,
    demo: Boolean = false
): Content<InlineKeyboardMarkup> {
    return Content (
        entities = giveawayEntities(giveaway, userLinkProvider),
        replyMarkup = giveawayMarkup (
            giveaway,
            participantsCount,
            demo
        )
    )
}
