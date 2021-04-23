package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.update.MyChatMemberUpdatedUpdate
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate


class MyChatMemberUpdate<out T> (
    override val bot: TelegramBot,
    override val di: T,
    update: MyChatMemberUpdatedUpdate
) : DIBotUpdate<T>, FromChatUpdate {
    override val chatId = update.data.chat.id.chatId
    val oldState = update.data.oldChatMemberState
    val newState = update.data.newChatMemberState
}
