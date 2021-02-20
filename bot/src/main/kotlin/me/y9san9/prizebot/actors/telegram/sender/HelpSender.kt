package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate


object HelpSender {
    suspend fun send(update: FromChatLocalizedBotUpdate) =
        update.sendMessage(update.locale.help)
}
