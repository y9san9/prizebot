package me.y9san9.prizebot.conditions

class RawConditionsClient(
    private val engine: SynchronizerEngine
) {
    suspend fun check(
        discriminator: Any,
        conditions: List<Condition>,
        firstHandler: suspend (Result) -> Unit
    ): Result = engine.execute(discriminator) {
        val invalidCondition = conditions
            .firstOrNull { condition -> !condition.check() }
            ?: return@execute Result.Success.also { firstHandler(it) }

        return@execute Result.Failed(invalidCondition).also { firstHandler(it) }
    }

    fun interface Condition {
        suspend fun check(): Boolean
    }

    sealed interface Result {
        object Success : Result
        class Failed(val condition: Condition) : Result
    }
}
