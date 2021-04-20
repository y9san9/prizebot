package me.y9san9.prizebot.handlers.channel_posts

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.chat.get.getChatAdministrators
import dev.inmo.tgbotapi.types.ChatId
import me.y9san9.prizebot.extensions.telegram.PrizebotChannelPostUpdate
import me.y9san9.telegram.updates.extensions.command.command


object ChannelPostsHandler {
    suspend fun handle(update: PrizebotChannelPostUpdate) = update.command {
        case("/connect_prizebot") {
            // If permission was not granted it is OK!
            safelyWithoutExceptions {
                update.delete()
            }

            val admins = update.bot.getChatAdministrators(ChatId(update.chatId))
            for(admin in admins) {
                update.di.linkChannel(admin.user.id.chatId, update.chatId)
            }
        }
    }
}
