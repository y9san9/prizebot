package me.y9san9.prizebot.logic.actor

import me.y9san9.prizebot.bot.shortcuts.*
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.telegram.extensions.editMessageTextSafe
import me.y9san9.telegram.extensions.userLinkProvider


object GiveawayMessageUpdater {
    suspend fun update (
        query: PrizebotCallbackQueryUpdate,
        giveaway: Giveaway,
        demo: Boolean = false
    ) {
        val inlineMessageId = query.inlineMessageId
        val message = query.message?.asTextContentMessage()

        if(inlineMessageId == null && message == null)
            return

        val content = giveawayContent (
            giveaway,
            query.di.participantsStorage.getParticipantsCount(giveaway.id),
            query.bot.userLinkProvider, demo
        )

        if(inlineMessageId != null)
            query.bot.editMessageTextSafe (
                inlineMessageId = inlineMessageId,
                content = content
            )

        if(message != null)
            query.bot.editMessageTextSafe (
                message = message,
                content = content
            )
    }
}
