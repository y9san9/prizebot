package me.y9san9.prizebot.extensions.telegram

import me.y9san9.telegram.updates.extensions.command.CommandDSL
import me.y9san9.telegram.updates.extensions.command.Default
import me.y9san9.telegram.updates.extensions.command.command
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate
import me.y9san9.telegram.updates.primitives.LocalizedUpdate


suspend inline fun <T> T.commandOrDefault(splitter: String = "\\s+", builder: CommandDSL.() -> Unit) where
        T : HasTextUpdate, T : FromChatLocalizedBotUpdate = command(splitter) {
    builder()

    default { default ->
        when(default) {
            is Default.NoTextMatched ->
                sendMessage(locale.unknownCommand(default.actualText))
            is Default.InvalidArgsCount ->
                sendMessage(locale.invalidArgsCount(default.expectedCount, default.actualCount))
            is Default.InvalidContent ->
                sendMessage(locale.enterText)
        }
    }
}
