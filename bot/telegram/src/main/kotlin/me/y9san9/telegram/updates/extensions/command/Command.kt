package me.y9san9.telegram.updates.extensions.command

import me.y9san9.telegram.updates.primitives.HasTextUpdate
import org.intellij.lang.annotations.Language


data class Command (
    val source: String,
    val text: String,
    val args: List<String>
)

inline operator fun Command?.invoke(builder: CommandDSL.() -> Unit) = CommandDSL(command = this).apply(builder)

fun String.parseCommand(@Language("RegExp") splitter: String = "\\s+"): Command {
    val source = trim()
    val parts = source.split(Regex(splitter))
    return Command(source = source, parts.first(), parts.drop(n = 1))
}

fun HasTextUpdate.command(splitter: String = "\\s+") = text?.parseCommand(splitter)

fun HasTextUpdate.requireCommand(splitter: String = "\\s+") = command(splitter)
    ?: error("Command required")
