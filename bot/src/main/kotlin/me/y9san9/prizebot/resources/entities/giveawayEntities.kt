package me.y9san9.prizebot.resources.entities

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.bold
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import me.y9san9.prizebot.actors.storage.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.LocalizedUpdate
import me.y9san9.telegram.utils.getUserLink


suspend fun giveawayEntities (
    update: PrizebotLocalizedBotUpdate, giveaway: Giveaway?
): TextSourcesList {
    if(giveaway == null)
        return update.locale.thisGiveawayDeleted

    val locale = giveaway.locale

    val title = bold(giveaway.title) + "\n\n"
//    val untilTime = "Until: 13 May 2021 14:00:00" + "\n\n"

    val ending = if(giveaway is FinishedGiveaway) {
        val link = update.bot.getUserLink(giveaway.winnerId, locale.deletedUser)
        regular("${locale.winner}: ") + link
    } else
        listOf(regular(locale.giveawayParticipateHint))

    return title +/* untilTime + */ending
}
