package me.y9san9.prizebot.handlers.choosen_inline_result.commands

import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.extensions.telegram.PrizebotChosenInlineResultUpdate

object PickGiveawayCommand {
    suspend fun handle(update: PrizebotChosenInlineResultUpdate) {
        val giveaway = update.di.getUserGiveaways (
            update.userId,
            count = 1,
            offset = update.resultId.string.toLongOrNull() ?: return
        ).getOrNull(0) ?: return

        update.di.addActiveMessage(
            giveaway.id,
            inlineMessage = GiveawaysActiveMessagesStorage.Message(
                id = update.inlineMessage ?: return,
                lastUpdateTime = System.currentTimeMillis()
            )
        )
    }
}
