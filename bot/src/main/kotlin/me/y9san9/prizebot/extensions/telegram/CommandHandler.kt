package me.y9san9.prizebot.extensions.telegram

import me.y9san9.telegram.updates.extensions.command.CommandDSL
import me.y9san9.telegram.updates.extensions.command.Default
import me.y9san9.telegram.updates.extensions.command.command
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.primitives.HasTextUpdate
import org.intellij.lang.annotations.Language


suspend inline fun <T> T.commandOrDefault(@Language("RegExp") splitter: String = "\\s+", builder: CommandDSL.() -> Unit) where
        T : HasTextUpdate, T : PrizebotLocalizedBotUpdate = command(splitter) {
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
