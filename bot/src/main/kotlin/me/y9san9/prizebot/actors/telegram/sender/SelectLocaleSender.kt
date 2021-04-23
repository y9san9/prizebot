package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.selectLocaleMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object SelectLocaleSender {
    suspend fun send(update: PrizebotMessageUpdate) {
        update.sendMessage (
            text = update.locale.selectLocale,
            replyMarkup = selectLocaleMarkup(update)
        )
    }
}
