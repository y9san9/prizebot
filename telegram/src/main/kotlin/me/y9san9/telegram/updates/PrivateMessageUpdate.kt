package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.abstracts.PrivateContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedUpdate
import me.y9san9.telegram.updates.primitives.*
import me.y9san9.telegram.utils.languageCode


class PrivateMessageUpdate<DI> (
    override val bot: TelegramBot,
    override val di: DI,
    message: PrivateContentMessage<*>,
) : DIUpdate<DI>, FromChatLocalizedBotUpdate, HasTextUpdate {

    override val languageCode: String? = message.languageCode
    override val chatId: Long = message.chat.id.chatId
    override val text: String? = (message.content as? TextContent)?.text

}
