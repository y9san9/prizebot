package me.y9san9.telegram.updates.message

import dev.inmo.tgbotapi.types.message.abstracts.PrivateMessage
import dev.inmo.tgbotapi.types.message.content.TextContent


inline fun BotMessageUpdate<*>.text(handler: (PrivateMessage<TextContent>) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    if(update.content is TextContent)
        handler(update as PrivateMessage<TextContent>)
}
