package me.y9san9.prizebot.handlers.channel_group_messages

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.ChatMember.abstracts.AdministratorChatMember
import dev.inmo.tgbotapi.types.UserId
import me.y9san9.extensions.any.unit
import me.y9san9.prizebot.actors.telegram.ChatLinkerActor
import me.y9san9.prizebot.extensions.telegram.PrizebotGroupMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.extensions.telegram_bot.getMeCached
import me.y9san9.telegram.updates.extensions.command.command
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object ChannelGroupMessagesHandler {
    suspend fun handle(update: PrizebotGroupMessageUpdate) = update.command {
        case("/connect_prizebot") {
            val sender = update.bot.getChatMember (
                ChatId(update.chatId),
                UserId(update.userId ?: return@command)
            )
            val bot = update.bot.getChatMember(ChatId(update.chatId), update.bot.getMeCached())

            if(sender !is AdministratorChatMember)
                return@command

            if(bot !is AdministratorChatMember)
                return@command update.sendMessage(update.locale.enterText).unit
            else safelyWithoutExceptions {
                update.delete()
            }

            ChatLinkerActor.link(update)
        }
    }
}
