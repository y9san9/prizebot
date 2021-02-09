package me.y9san9.prizebot.bot.shortcuts

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.*
import me.y9san9.prizebot.logic.utils.awesomeCut
import me.y9san9.prizebot.models.ActiveGiveaway
import me.y9san9.prizebot.models.FinishedGiveaway
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.prizebot.models.locale
import me.y9san9.prizebot.resources.locales.Locale


suspend fun giveawayEntities(giveaway: Giveaway, userLinkProvider: suspend (Long) -> TextMentionTextSource?): TextSourcesList {
    val locale = giveaway.locale

    val title = bold(giveaway.title) + "\n\n"
//    val untilTime = "Until: 13 May 2021 14:00:00" + "\n\n"

    val ending = if(giveaway is FinishedGiveaway) {
        val link = userLinkProvider(giveaway.winnerId) ?: regular(locale.unknownUserDeletedUsername)
        regular("${locale.winner}: ") + link
    } else
        listOf(regular(locale.giveawayParticipateHint))

    return title +/* untilTime + */ending
}

fun selfGiveawaysMessage(languageCode: String?, giveawaysTitles: List<String>, offset: Long): TextSourcesList {
    val giveawaysTitle = regular("${Locale.with(languageCode).selectGiveawayToView}: \n\n")
    val giveaways = giveawaysTitles.flatMapIndexed { i, title ->
        regular("${offset + i + 1}. ") + bold(title.awesomeCut(maxLength = 25)) + "\n"
    }

    return giveawaysTitle + giveaways
}

fun giveawayDeletedEntities(locale: Locale) = locale.selectGiveawayToView
