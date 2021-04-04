package me.y9san9.prizebot.actors.telegram.sender

import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object GiveawayParticipateTextInputSender {
    suspend fun send(event: PrizebotLocalizedBotUpdate) {
        event.sendMessage(event.locale.giveawayParticipateInput)
    }
}
