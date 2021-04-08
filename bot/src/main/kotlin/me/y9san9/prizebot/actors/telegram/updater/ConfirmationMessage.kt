package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.resources.markups.confirmationMarkup
import me.y9san9.telegram.extensions.asTextContentMessage


object ConfirmationMessage {
    suspend fun confirm (
        update: PrizebotCallbackQueryUpdate,
        confirmationText: String,
        confirmationAction: String,
        cancelAction: String
    ) {
        val message = update.message?.asTextContentMessage() ?: return

        update.bot.editMessageText (
            message,
            update.locale.confirmation(confirmationText),
            replyMarkup = confirmationMarkup(update, confirmationAction, cancelAction)
        )
    }
}
