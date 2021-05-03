package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.extensions.command.*
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate
import org.intellij.lang.annotations.Language


suspend inline fun <T> T.commandOrDefault (
    @Language("RegExp") splitter: String = "\\s+",
    noinline noTextMatchedMatched: suspend (Default.NoTextMatched) -> Unit =
        { sendMessage(locale.unknownCommand(it.actualText)) },
    noinline invalidArgsCount: suspend (Default.InvalidArgsCount) -> Unit =
        { sendMessage(locale.invalidArgsCount(it.expectedCount, it.actualCount)) },
    noinline invalidContent: suspend (Default.InvalidContent) -> Unit =
        { sendMessage(locale.enterText) },
    builder: CommandDSL.() -> Unit
) where T : HasTextUpdate, T : PrizebotLocalizedBotUpdate,
        T : FromChatUpdate = command(splitter) {
    builder()

    default { default ->
        when(default) {
            is Default.NoTextMatched -> noTextMatchedMatched(default)
            is Default.InvalidArgsCount -> invalidArgsCount(default)
            is Default.InvalidContent -> invalidContent(default)
        }
    }
}


@CommandDSLMarker
inline fun CommandDSL.raw (
    noinline getter: (Locale) -> String,
    action: CommandContext.() -> Unit
) = raw(Locale.all(getter), action)
