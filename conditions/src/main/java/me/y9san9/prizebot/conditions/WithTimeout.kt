package me.y9san9.prizebot.conditions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.withTimeoutOrNull

suspend fun RawConditionsClient.checkWithTimeout(
    scope: CoroutineScope,
    timeMillis: Long,
    discriminator: Any,
    conditions: List<RawConditionsClient.Condition>,
    firstHandler: suspend (RawConditionsClient.Result) -> Unit
): TimeoutResult {
    val deferred = scope.async { check(discriminator, conditions, firstHandler) }
    return withTimeoutOrNull(timeMillis) { TimeoutResult.Success(deferred.await()) }
        ?: TimeoutResult.Timeout(deferred)
}

sealed interface TimeoutResult {
    class Success(val underlying: RawConditionsClient.Result) : TimeoutResult
    class Timeout(val deferred: Deferred<RawConditionsClient.Result>) : TimeoutResult
}
