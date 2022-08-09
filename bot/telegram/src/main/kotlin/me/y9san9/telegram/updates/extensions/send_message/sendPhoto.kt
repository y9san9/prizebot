package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.extensions.api.send.media.sendPhoto
import dev.inmo.tgbotapi.requests.abstracts.FileId
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.textsources.TextSource
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import me.y9san9.telegram.updates.hierarchies.PossiblyFromUserBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate


/**
 * filename to fileId
 */
private val cache = mutableMapOf<String, FileId>()
/**
 * Photo cached by name
 */
suspend fun <T> T.sendPhotoCached (
    file: MultipartFile,
    entities: TextSourcesList,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = if(file.filename in cache)
    sendPhoto (
        cache.getValue(file.filename), entities, disableNotification,
        protectContent, replyToMessageId, allowSendingWithoutReply, replyMarkup
    )
else
    sendPhoto (
        file, entities, disableNotification, protectContent,
        replyToMessageId, allowSendingWithoutReply, replyMarkup
    ).apply { cache[file.filename] = content.media.fileId }


suspend fun <T> T.sendPhoto (
    fileId: InputFile,
    entities: TextSourcesList,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendPhoto (
    ChatId(chatId), fileId, entities,
    disableNotification, protectContent, replyToMessageId,
    allowSendingWithoutReply, replyMarkup
)
