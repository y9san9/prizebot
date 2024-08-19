package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.resources.markups.selectLocaleMarkup
import me.y9san9.telegram.extensions.asTextContentMessage


object SelectLocaleUpdater {
    suspend fun update(update: PrizebotCallbackQueryUpdate) {
        val message = update.message?.asTextContentMessage() ?: return

        safelyWithoutExceptions {
            update.bot.editMessageText (
                message = message,
                text = update.getLocale().selectLocale,
                replyMarkup = selectLocaleMarkup(update)
            )
        }
    }
}
