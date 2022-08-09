package me.y9san9.prizebot.resources.entities

import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.italic
import dev.inmo.tgbotapi.types.message.textsources.mention
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.prizebot.database.giveaways_storage.locale
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage
import me.y9san9.prizebot.extensions.emoji.getPlaceEmoji
import me.y9san9.prizebot.resources.Emoji
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun giveawayEntities (
    titlesStorage: UserTitlesStorage,
    giveaway: Giveaway
): TextSourcesList {
    val locale = giveaway.locale

    val title = bold(giveaway.title) + ""

    val raffleDate = giveaway.raffleDate

    val untilTime = if(raffleDate == null) listOf() else {
        val nowDate = OffsetDateTime.now(raffleDate.offset)

        val pattern = if (
            raffleDate.year != nowDate.year &&
            Duration.between(nowDate, raffleDate) > Duration.ofDays(100)
        ) "d MMMM yyyy, HH:mm (XXX)" else "d MMMM, HH:mm (XXX)"

        val format = DateTimeFormatter.ofPattern (
            pattern, Locale.forLanguageTag(giveaway.languageCode)
        )
        val date = raffleDate.format(format)
        bold(locale.raffleDate) + ": $date" + ""
    }

    val winnersCount = if(giveaway is ActiveGiveaway && giveaway.winnersCount.value > 1)
        bold(locale.winnersCount) + ": ${giveaway.winnersCount.value}"
    else listOf()

    val conditions = giveaway.conditions.list
    val conditionsEntities = if(conditions.isNotEmpty()) {
        bold(locale.giveawayConditions) + "\n" +
                conditions
                    .sortedBy { if(it is Condition.Invitations) 0 else 1 }
                    .flatMap { condition ->
                        when(condition) {
                            is Condition.Subscription ->
                                regular("• ") + locale.subscribeTo(condition.channelUsername)
                            is Condition.Invitations ->
                                regular("• ") + locale.inviteFriends(condition.count.int)
                        } + "\n"
                    }.dropLast(n = 1)
    } else listOf()

    val winners = if(giveaway is FinishedGiveaway) {
        val links = giveaway.winnerIds
            .map { id -> id.mention(text = titlesStorage.getUserTitle(id) ?: locale.deletedUser) }
            .flatMapIndexed { i, mention ->
                if(giveaway.displayWinnersWithEmojis)
                    regular(Emoji.getPlaceEmoji(place = i + 1)) + " " + mention + regular("\n")
                else
                    mention + regular(", ")
            }
            .dropLast(n = 1)

        regular("${locale.winner(plural = giveaway.winnerIds.size > 1)}: ") +
                (if(giveaway.displayWinnersWithEmojis) "\n" else "") + links
    } else listOf()

    val participateHint = if(giveaway is ActiveGiveaway)
        italic(locale.giveawayParticipateHint) + ""
    else listOf()

    return listOf (
        title, winnersCount, untilTime, conditionsEntities, winners, participateHint
    ).flatMap { if(it.isEmpty()) it else it + "\n\n" }
        .dropLast(n = 1)
}
