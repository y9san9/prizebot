package me.y9san9.prizebot.handlers.choosen_inline_result.commands

import me.y9san9.prizebot.extensions.telegram.PrizebotChosenInlineResultUpdate


object SendGiveawayCommand {
    fun handle(update: PrizebotChosenInlineResultUpdate) {
        update.di.addActiveMessage (
            giveawayId = update.resultId.toLongOrNull() ?: return,
            inlineMessageId = update.inlineMessage ?: return
        )
    }
}
