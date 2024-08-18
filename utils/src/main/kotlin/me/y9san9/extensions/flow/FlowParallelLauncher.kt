package me.y9san9.extensions.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.y9san9.aqueue.flow.launchInAQueue

fun <T> Flow<T>.launchEachSafely(
    scope: CoroutineScope,
    throwableHandler: suspend (Throwable) -> Unit = { throw it },
    key: suspend (T) -> Any? = { null },
    consumer: suspend (T) -> Unit
) = launchInAQueue(scope, key = key) {
    try { consumer(it) } catch (t: Throwable) { throwableHandler(t) }
}
