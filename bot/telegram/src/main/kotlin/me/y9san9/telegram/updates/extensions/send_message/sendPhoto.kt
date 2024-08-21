package me.y9san9.telegram.updates.extensions.send_message

import dev.inmo.tgbotapi.extensions.api.send.media.sendPhoto
import dev.inmo.tgbotapi.requests.abstracts.FileId
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
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
    threadId: MessageThreadId? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyParameters: ReplyParameters? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = if(file.filename in cache)
    sendPhoto(
        fileId = cache.getValue(file.filename),
        entities = entities,
        threadId = threadId,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyParameters = replyParameters,
        replyMarkup = replyMarkup
    )
else
    sendPhoto(
        fileId = file,
        entities = entities,
        threadId = threadId,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyParameters = replyParameters,
        replyMarkup = replyMarkup
    ).apply {
        cache[file.filename] = content.media.fileId
    }


suspend fun <T> T.sendPhoto (
    fileId: InputFile,
    entities: TextSourcesList,
    threadId: MessageThreadId? = null,
    disableNotification: Boolean = false,
    protectContent: Boolean = false,
    replyParameters: ReplyParameters? = null,
    replyMarkup: KeyboardMarkup? = null
) where T : BotUpdate, T : FromChatUpdate = bot.sendPhoto(
    chatId = chatId.toChatId(),
    fileId = fileId,
    entities = entities,
    threadId = threadId,
    disableNotification = disableNotification,
    protectContent = protectContent,
    replyParameters = replyParameters,
    replyMarkup = replyMarkup
)
