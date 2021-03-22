package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.mainMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object CancellationToMainStateSender {
    suspend fun send(event: PrizebotPrivateMessageUpdate) {
        event.sendMessage (
            event.locale.cancelled,
            replyMarkup = mainMarkup(event)
        )
    }
}
