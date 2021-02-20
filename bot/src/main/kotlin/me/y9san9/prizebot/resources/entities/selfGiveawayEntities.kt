package me.y9san9.prizebot.resources.entities

import dev.inmo.tgbotapi.CommonAbstracts.TextSourcesList
import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.bold
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import me.y9san9.prizebot.extensions.awesomeCut
import me.y9san9.prizebot.resources.locales.Locale


fun selfGiveawaysEntities(languageCode: String?, giveawaysTitles: List<String>, offset: Long): TextSourcesList {
    val giveawaysTitle = regular("${Locale.with(languageCode).selectGiveawayToView}: \n\n")
    val giveaways = giveawaysTitles.flatMapIndexed { i, title ->
        regular("${offset + i + 1}. ") + bold(title.awesomeCut(maxLength = 25)) + "\n"
    }

    return giveawaysTitle + giveaways
}
