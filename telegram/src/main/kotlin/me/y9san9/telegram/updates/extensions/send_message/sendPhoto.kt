package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.CommonAbstracts.TextSource
import dev.inmo.tgbotapi.extensions.api.send.media.sendPhoto
import dev.inmo.tgbotapi.requests.abstracts.FileId
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import me.y9san9.telegram.updates.hierarchies.FromUserBotUpdate


/**
 * filename to fileId
 */
private val cache = mutableMapOf<String, FileId>()
/**
 * Photo cached by name
 */
suspend fun FromUserBotUpdate.sendPhotoCached (
    file: MultipartFile,
    entities: List<TextSource>,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = if(file.filename in cache)
    sendPhoto (
        cache.getValue(file.filename), entities, disableNotification,
        replyToMessageId, allowSendingWithoutReply,
        replyMarkup
    )
else
    sendPhoto (
        file, entities, disableNotification, replyToMessageId,
        allowSendingWithoutReply, replyMarkup
    ).apply { cache[file.filename] = content.media.fileId }


suspend fun FromUserBotUpdate.sendPhoto (
    file: InputFile,
    entities: List<TextSource>,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = bot.sendPhoto (
    ChatId(userId), file,
    entities, disableNotification,
    replyToMessageId, allowSendingWithoutReply,
    replyMarkup
)
