package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.update.MyChatMemberUpdatedUpdate
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.FromUserUpdate


class MyChatMemberUpdate<out T> (
    override val bot: TelegramBot,
    override val di: T,
    update: MyChatMemberUpdatedUpdate
) : DIBotUpdate<T>, FromUserUpdate, FromChatUpdate {
    override val userId = update.data.chat.id.chatId.long
    val oldState = update.data.oldChatMemberState
    val newState = update.data.newChatMemberState
    override val chatId = update.data.chat.id.chatId.long

}
