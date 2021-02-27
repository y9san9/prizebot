package me.y9san9.prizebot.handlers.choosen_inline_result.commands

import me.y9san9.prizebot.extensions.telegram.PrizebotChosenInlineResultUpdate

object PickGiveawayCommand {
    fun handle(update: PrizebotChosenInlineResultUpdate) {
        val giveaway = update.di.getUserGiveaways (
            update.chatId,
            count = 1,
            offset = update.resultId.toLongOrNull() ?: return
        ).getOrNull(0) ?: return

        update.di.addActiveMessage(giveaway.id, inlineMessageId = update.inlineMessage ?: return)
    }
}
