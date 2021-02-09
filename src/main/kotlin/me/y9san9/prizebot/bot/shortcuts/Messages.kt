package me.y9san9.prizebot.bot.shortcuts

import dev.inmo.tgbotapi.extensions.utils.asContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.abstracts.PrivateMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.message.*


@Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST")
fun Message.asTextContentMessage() = asContentMessage()
    ?.takeIf { it.content is TextContent } as? ContentMessage<TextContent>

suspend inline fun BotMessageUpdate<*>.commandOrDefault(handler: CommandDSL.() -> Unit) = command {
    handler()
    defaultHandler(event = this@commandOrDefault)
}

suspend fun CommandDSL.defaultHandler(event: BotMessageUpdate<*>) = default { type ->
    when(type) {
        is DefaultType.NoTextMatched -> event.sendMessage (
            text = Locale.with(event.languageCode).unknownCommand(type.actualText)
        )
        is DefaultType.InvalidArgsCount -> event.sendMessage (
            text = Locale.with(event.languageCode).invalidArgsCount(type.expectedCount, type.actualCount)
        )
        is DefaultType.InvalidContent -> event.sendMessage (
            text = Locale.with(event.languageCode).enterText
        )
    }
}

suspend inline fun BotMessageUpdate<*>.textOrDefault(handler: (PrivateMessage<TextContent>) -> Unit) {
    text(handler)
    sendEnterTextMessage()
}

suspend fun BotMessageUpdate<*>.sendEnterTextMessage() = sendMessage (
    text = Locale.with(languageCode).enterText
)

suspend fun PrizebotMessageUpdate.sendSelfGiveawaysMessage() {
    val storage = di.giveawaysStorage

    val content = selfGiveawaysContent(chatId, languageCode, storage)

    if(content == null) sendMessage (
        text = Locale.with(languageCode).noGiveawaysYet
    ) else sendMessage (
        entities = content.entities,
        replyMarkup = content.replyMarkup
    )
}
