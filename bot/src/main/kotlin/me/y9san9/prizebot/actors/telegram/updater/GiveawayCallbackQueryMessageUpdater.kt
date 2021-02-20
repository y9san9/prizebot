package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.models.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.utils.asTextContentMessage


object GiveawayCallbackQueryMessageUpdater {
    suspend fun update (
        query: PrizebotCallbackQueryUpdate,
        giveaway: Giveaway,
        demo: Boolean = false
    ) {
        val inlineMessageId = query.inlineMessageId
        val message = query.message?.asTextContentMessage()

        if(inlineMessageId == null && message == null)
            return

        val (entities, markup) = giveawayContent(query, giveaway, demo)

        if(inlineMessageId != null) safely {
            query.bot.editMessageText (
                inlineMessageId = inlineMessageId,
                entities = entities,
                replyMarkup = markup
            )
        }

        if(message != null) safely {
            query.bot.editMessageText (
                message = message,
                entities = entities,
                replyMarkup = markup
            )
        }
    }
}
