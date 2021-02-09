package me.y9san9.telegram.updates.inline_query

import me.y9san9.telegram.updates.message.CommandDSL
import me.y9san9.telegram.updates.message.parseCommand


inline fun BotInlineQueryUpdate<*>.command(splitter: String = "_", handler: CommandDSL.() -> Unit) {
    val command = update.data.query.parseCommand(splitter)
    CommandDSL(command).apply(handler)
}
