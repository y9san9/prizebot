package me.y9san9.prizebot.handlers.callback_queries.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.resources.content.selfGiveawaysContent
import me.y9san9.telegram.updates.extensions.command.requireCommand
import me.y9san9.telegram.utils.asTextContentMessage


object SelfGiveawaysButtonsCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val offset = update.requireCommand(splitter = "_").args[1].toLongOrNull() ?: return
        if(offset < 0) return

        val message = update.message?.asTextContentMessage() ?: return

        val (entities, markup) = selfGiveawaysContent(update, offset) ?: return

        update.bot.editMessageText(message, entities, replyMarkup = markup)
        update.answer()
    }
}
