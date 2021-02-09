package me.y9san9.telegram.updates.callback_query

import dev.inmo.tgbotapi.types.CallbackQuery.DataCallbackQuery
import me.y9san9.telegram.updates.message.CommandDSL
import me.y9san9.telegram.updates.message.parseCommand


inline fun BotCallbackQueryUpdate<*>.command(splitter: String = "_", handler: CommandDSL.() -> Unit) {
    val command = (update.data as? DataCallbackQuery)?.data?.parseCommand(splitter)
    CommandDSL(command).apply(handler)
}
