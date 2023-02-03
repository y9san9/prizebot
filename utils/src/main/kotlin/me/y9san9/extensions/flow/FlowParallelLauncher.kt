package me.y9san9.extensions.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * Main use-case of this class is function user events:
 *  events from different users should be parallel while
 *  events from one user should come consistently
 */
fun <T> Flow<T>.parallelEach(
    scope: CoroutineScope,
    /**
     * @return null if there is no need in consistence
     */
    consistentKey: suspend (T) -> Any? = { null },
    consumer: suspend (T) -> Unit
) {
    val mutex = Mutex()
    val jobs = mutableMapOf<Any, Job>()

    onEach { item ->
        val key = consistentKey(item)

        if (key == null) {
            scope.launch {
                consumer(item)
            }
            return@onEach
        }

        mutex.withLock {
            val job = jobs[key]
            jobs[key] = scope.launch {
                job?.join()
                consumer(item)
            }
        }
    }.launchIn(scope)
}

fun <T> Flow<T>.launchEachSafely (
    coroutineScope: CoroutineScope,
    throwableHandler: suspend (Throwable) -> Unit = { throw it },
    mutexKey: suspend (T) -> Any? = { null },
    consumer: suspend (T) -> Unit
) = parallelEach(coroutineScope, mutexKey) {
    try { consumer(it) } catch (t: Throwable) { throwableHandler(t) }
}
