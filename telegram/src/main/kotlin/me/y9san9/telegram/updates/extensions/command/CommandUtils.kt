package me.y9san9.telegram.updates.extensions.command

import me.y9san9.telegram.updates.primitives.AnswerableUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


inline fun HasTextUpdate.command(splitter: String = "\\s+", builder: CommandDSL.() -> Unit) {
    CommandDSL(text?.parseCommand(splitter)).apply(builder)
}

suspend inline fun <T> T.commandOrAnswer (
    splitter: String = "\\s", builder: CommandDSL.() -> Unit
) where T : HasTextUpdate, T : AnswerableUpdate = command(splitter) {
    builder()

    default {
        answer()
    }
}
