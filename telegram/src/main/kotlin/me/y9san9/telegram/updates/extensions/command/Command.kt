package me.y9san9.telegram.updates.extensions.command

import me.y9san9.telegram.updates.primitives.HasTextUpdate


data class Command (
    val source: String,
    val text: String,
    val args: List<String>
)

fun String.parseCommand(splitter: String = "\\s+"): Command {
    val source = trim()
    val parts = source.split(Regex(splitter))
    return Command(source = source, parts.first(), parts.drop(n = 1))
}

fun HasTextUpdate.command(splitter: String = "\\s+") = text?.parseCommand(splitter)

fun HasTextUpdate.requireCommand(splitter: String = "\\s+") = command(splitter)
    ?: error("Command required")
