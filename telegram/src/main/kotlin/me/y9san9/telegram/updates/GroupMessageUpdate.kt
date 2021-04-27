package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage

class GroupMessageUpdate<out DI> (
    bot: TelegramBot,
    di: DI,
    message: ContentMessage<*>,
) : MessageUpdate<DI>(bot, di, message) {
    val chatId = message.chat.id.chatId
}
