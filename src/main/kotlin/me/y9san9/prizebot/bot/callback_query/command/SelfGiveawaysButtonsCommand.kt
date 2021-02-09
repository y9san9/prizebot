package me.y9san9.prizebot.bot.callback_query.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.bot.shortcuts.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.bot.shortcuts.asTextContentMessage
import me.y9san9.prizebot.bot.shortcuts.selfGiveawaysContent
import me.y9san9.telegram.updates.message.Command


object SelfGiveawaysButtonsCommand {
    suspend fun handle(event: PrizebotCallbackQueryUpdate, command: Command) {
        val offset = command.args[1].toLongOrNull() ?: return
        if(offset < 0) return

        val storage = event.di.giveawaysStorage
        val userId = event.from.id.chatId

        val message = event.message?.asTextContentMessage() ?: return

        val content = selfGiveawaysContent(userId, event.languageCode, storage, offset) ?: return

        event.bot.editMessageText(message, content.entities, replyMarkup = content.replyMarkup)
        event.answer()
    }
}
