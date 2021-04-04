package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object HelpSender {
    suspend fun send(update: PrizebotLocalizedBotUpdate) =
        update.sendMessage(update.locale.help)
}
