package me.y9san9.telegram.updates.extensions.command


@DslMarker
annotation class CommandDSLMarker

@CommandDSLMarker
data class CommandContext (
    val command: Command
)

sealed class Default {
    object InvalidContent : Default()
    data class NoTextMatched(val actualText: String) : Default()
    data class InvalidArgsCount(val expectedCount: Int, val actualCount: Int) : Default()
}

@CommandDSLMarker
class CommandDSL (
    @PublishedApi internal val command: Command?
) {
    /**
     * if [result] is null then successfully handled
     */
    @PublishedApi
    internal var result: Default? = if(command == null)
        Default.InvalidContent
    else
        Default.NoTextMatched(actualText = command.text)

    @CommandDSLMarker
    inline fun case(vararg texts: String, argsCount: Int? = null, handler: CommandContext.() -> Unit) {
        if(command != null && result != null) {
            if(texts.any(command.text::equals)) {
                result = if (argsCount == null || command.args.size == argsCount) {
                    handler(CommandContext(command)); null
                } else {
                    Default.InvalidArgsCount(expectedCount = argsCount, actualCount = command.args.size)
                }
            }
        }
    }

    @CommandDSLMarker
    inline fun raw(vararg texts: String, action: CommandContext.() -> Unit) {
        if(command != null && result != null) {
            if(texts.any(command.source::equals)) {
                action(CommandContext(command))
                result = null
            }
        }
    }

    @CommandDSLMarker
    inline fun default(handler: (Default) -> Unit) {
        if(result != null) {
            handler(result!!)
            result = null
        }
    }
}
