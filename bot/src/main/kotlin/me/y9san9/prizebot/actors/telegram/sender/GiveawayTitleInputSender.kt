package me.y9san9.prizebot.actors.telegram.sender

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate


object GiveawayTitleInputSender {
    suspend fun send(update: FromChatLocalizedBotUpdate) = update.sendMessage (
        text = update.locale.giveawayTitleInput,
        replyMarkup = ReplyKeyboardRemove()
    )
}
