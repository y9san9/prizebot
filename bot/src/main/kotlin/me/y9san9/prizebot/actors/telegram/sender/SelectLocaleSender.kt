package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.resources.markups.selectLocaleMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object SelectLocaleSender {
    suspend fun send(update: PrizebotPrivateMessageUpdate) {
        update.sendMessage (
            text = update.getLocale().selectLocale,
            replyMarkup = selectLocaleMarkup(update)
        )
    }
}
