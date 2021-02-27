package me.y9san9.prizebot.actors.storage.giveaways_active_messages_storage

import com.kotlingang.kds.mutate
import dev.inmo.tgbotapi.types.InlineMessageIdentifier
import me.y9san9.prizebot.actors.storage.kds.KDS
import me.y9san9.prizebot.resources.ACTIVE_MESSAGES_LIMIT


internal class KDSGiveawaysActiveMessagesStorage : GiveawaysActiveMessagesStorage {
    override fun addActiveMessage (giveawayId: Long, inlineMessageId: InlineMessageIdentifier) = KDS.mutate {
        activeMessages += ActiveMessage(giveawayId, inlineMessageId)

        val extra = activeMessages.size - ACTIVE_MESSAGES_LIMIT

        if(extra > 0)
            activeMessages.dropLast(extra)
    }

    override fun getActiveMessage(giveawayId: Long) = KDS.activeMessages
        .filter { it.giveawayId == giveawayId }
        .map { it.messageId }
}

