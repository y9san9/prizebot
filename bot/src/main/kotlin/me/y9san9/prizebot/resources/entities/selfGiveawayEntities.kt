package me.y9san9.prizebot.resources.entities

import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.locales.Locale


fun selfGiveawaysEntities(locale: Locale, giveawaysTitles: List<String>, offset: Long): TextSourcesList {
    val giveawaysTitle = regular("${locale.selectGiveawayToView}: \n\n")
    val giveaways = giveawaysTitles.flatMapIndexed { i, title ->
        regular("${offset + i + 1}. ") + bold(title.awesomeCut(maxLength = 25)) + "\n"
    }

    return giveawaysTitle + giveaways
}
