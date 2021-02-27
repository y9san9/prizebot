package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.utils.asTextContentMessage


object GiveawayCallbackQueryMessageUpdater {

    suspend fun update (
        update: PrizebotCallbackQueryUpdate,
        demo: Boolean = false,
        splitter: String = "_"
    ) {
        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter) ?: return
        update(update, giveaway, demo)
    }

    suspend fun update (
        update: PrizebotCallbackQueryUpdate,
        giveaway: Giveaway?,
        demo: Boolean = false
    ) {
        val inlineMessageId = update.inlineMessageId
        val message = update.message?.asTextContentMessage()

        if(inlineMessageId == null && message == null)
            return

        val (entities, markup) = giveawayContent(update, giveaway, demo)

        if(inlineMessageId != null) safelyWithoutExceptions {
            update.bot.editMessageText (
                inlineMessageId = inlineMessageId,
                entities = entities,
                replyMarkup = markup
            )
        }

        if(message != null) safelyWithoutExceptions {
            update.bot.editMessageText (
                message = message,
                entities = entities,
                replyMarkup = markup
            )
        }
    }
}
