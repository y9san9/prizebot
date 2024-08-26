package me.y9san9.prizebot.conditions

import me.y9san9.aqueue.AQueue

class RawConditionsClient(
    private val queue: AQueue
) {
    suspend fun check(
        discriminator: Any,
        conditions: List<Condition>,
        firstHandler: suspend (Result) -> Unit
    ): Result = queue.execute(discriminator) {
        val invalidCondition = conditions
            .firstOrNull { condition ->
                !condition.check()
            }
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
