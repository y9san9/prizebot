package me.y9san9.prizebot.resources.entities

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.bold
import dev.inmo.tgbotapi.types.MessageEntity.textsources.italic
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import dev.inmo.tgbotapi.types.MessageEntity.textsources.underline
import me.y9san9.prizebot.actors.storage.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.utils.getUserLink
import java.time.format.DateTimeFormatter
import java.util.*


suspend fun giveawayEntities (
    update: BotUpdate,
    giveaway: Giveaway
): TextSourcesList {
    val locale = giveaway.locale

    val title = bold(giveaway.title) + "\n\n"

    val untilTime = if(giveaway.raffleDate == null) listOf() else {
        val format = DateTimeFormatter.ofPattern (
            "HH:mm dd.MM X", Locale.forLanguageTag(giveaway.languageCode)
        )
        val date = giveaway.raffleDate!!.format(format)
        underline(locale.raffleDate) + ": $date" + "\n\n"
    }

    val ending = if(giveaway is FinishedGiveaway) {
        val link = update.bot.getUserLink(giveaway.winnerId, locale.deletedUser)
        regular("${locale.winner}: ") + link
    } else
        listOf(italic(locale.giveawayParticipateHint))

    return title + untilTime + ending
}
