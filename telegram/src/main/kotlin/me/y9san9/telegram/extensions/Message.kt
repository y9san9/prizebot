package me.y9san9.telegram.extensions

import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.FromUserMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.content.TextContent


val FromUserMessage.languageCode get() = (user as? CommonUser)?.languageCode

@Suppress("UNCHECKED_CAST")
fun Message.asTextContentMessage() = (this as? ContentMessage<*>)
    ?.takeIf { it.content is TextContent } as ContentMessage<TextContent>?
