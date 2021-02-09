package me.y9san9.telegram.updates.message

import dev.inmo.tgbotapi.types.message.content.TextContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter


sealed class DefaultType {
    data class NoTextMatched(val actualText: String) : DefaultType()
    object InvalidContent : DefaultType()
    data class InvalidArgsCount(val expectedCount: Int, val actualCount: Int) : DefaultType()
}

class CommandDSL (val command: Command?) {
    @PublishedApi
    internal var requestedDefault: DefaultType? = DefaultType.NoTextMatched(actualText = command?.source ?: "null")
        private set

    @PublishedApi
    internal val needHandle get() = requestedDefault !is DefaultType.InvalidContent

    inline fun case(vararg text: String, argsCount: Int? = null, action: (Command) -> Unit) {
        if(needHandle) {
            command ?: return callDefault(DefaultType.InvalidContent)
            val realText = text.map(String::trim)

            if (realText.any(command.text::equals))
                if (argsCount == null || command.args.size == argsCount) {
                    action(command)
                    setHandled()
                } else callDefault (
                    DefaultType.InvalidArgsCount(
                        expectedCount = argsCount,
                        actualCount = command.args.size
                    )
                )
        }
    }

    inline fun raw(vararg text: String, action: (Command) -> Unit) {
        if(needHandle) {
            command ?: return callDefault(DefaultType.InvalidContent)
            val realText = text.map(String::trim)

            if(realText.any(command.source::equals)) {
                action(command)
                setHandled()
            }
        }
    }

    inline fun default(action: (DefaultType) -> Unit) {
        val defaultType = requestedDefault

        if(defaultType != null) {
            action(defaultType)
            setHandled()
        }
    }

    @PublishedApi
    internal fun callDefault(defaultType: DefaultType) {
        requestedDefault = defaultType
    }
    @PublishedApi
    internal fun setHandled() {
        requestedDefault = null
    }
}

inline fun BotMessageUpdate<*>.command(handler: CommandDSL.() -> Unit) {
    val command = (update.content as? TextContent)?.text?.parseCommand()
    CommandDSL(command).apply(handler)
}

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
