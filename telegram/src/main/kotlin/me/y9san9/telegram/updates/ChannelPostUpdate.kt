package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.delete
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.message.abstracts.*
import me.y9san9.telegram.extensions.asTextContentMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.DeletableUpdate
import me.y9san9.telegram.updates.primitives.FromGroupUserUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


class ChannelPostUpdate<out DI> (
    override val bot: TelegramBot,
    override val di: DI,
    private val message: ChannelContentMessage<*>,
) : FromChatLocalizedDIBotUpdate<DI>, HasTextUpdate, FromGroupUserUpdate, DeletableUpdate {

    override val chatId: Long = message.chat.id.chatId
    override val languageCode: String? = ((message as? FromUserMessage)?.user as? CommonUser)?.languageCode
    override val text: String? = message.asTextContentMessage()?.content?.text
    override val userId: Long? = (message as? FromUserMessage)?.user?.id?.chatId

    override suspend fun delete() = message.delete(bot).let { }

}
