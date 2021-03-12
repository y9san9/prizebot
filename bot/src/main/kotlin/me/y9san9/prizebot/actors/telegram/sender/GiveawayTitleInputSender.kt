package me.y9san9.prizebot.actors.telegram.sender

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.prizebot.extensions.telegram.PrizebotLocalizedBotUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object GiveawayTitleInputSender {
    suspend fun send(update: PrizebotLocalizedBotUpdate) = update.sendMessage (
        text = update.locale.giveawayTitleInput,
        replyMarkup = ReplyKeyboardRemove()
    )
}
