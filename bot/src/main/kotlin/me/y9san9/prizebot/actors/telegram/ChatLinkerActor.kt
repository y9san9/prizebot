package me.y9san9.prizebot.actors.telegram

import dev.inmo.tgbotapi.bot.exceptions.RequestException
import dev.inmo.tgbotapi.extensions.api.chat.get.getChatAdministrators
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.toChatId
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate


object ChatLinkerActor {
    suspend fun <T> link(update: T) where T : DIBotUpdate<PrizebotDI>, T : FromChatUpdate {
        val admins = try {
            update.bot.getChatAdministrators(update.chatId.toChatId())
        } catch (_: RequestException) {
            return
        }

        for (admin in admins) {
            update.di.linkChannel(admin.user.id.chatId.long, update.chatId)
        }
    }
}
