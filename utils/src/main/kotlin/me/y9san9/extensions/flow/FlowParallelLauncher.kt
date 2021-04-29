package me.y9san9.extensions.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex


/**
 * Main use-case of this class is paralleling user events:
 *  events from different users should be parallel while
 *  events from one user should come consistently
 */
class FlowParallelLauncher<T> (
    private val flow: Flow<T>
) {
    private val mutexMap = mutableMapOf<Any, Mutex>()
    private val lockedMutexCount = mutableMapOf<Mutex, Int>()

    fun launchEach (
        coroutineScope: CoroutineScope,
        /**
         * @return null if there is no need in synchronization
         */
        mutexKey: suspend (T) -> Any? = { null },
        consumer: suspend (T) -> Unit
    ) = flow.onEach { item ->
        coroutineScope.launch {
            val key = mutexKey(item)

            val mutex = if(key == null)
                null
            else synchronized(lock = this@FlowParallelLauncher) {
                mutexMap.getOrPut(key) { Mutex() }.also { mutex ->
                    lockedMutexCount.compute(mutex) { _, count -> count?.inc() ?: 0 }
                }
            }

            mutex?.lock()
            try {
                consumer(item)
            } finally {
                mutex?.unlock()
            }

            if(mutex != null) synchronized(lock = this@FlowParallelLauncher) {
                lockedMutexCount.compute(mutex) { _, count -> count?.dec() ?: 0 }
            }

            key?.let(::cleanMap)
        }
    }.launchIn(coroutineScope)

    private fun cleanMap(key: Any) = synchronized(lock = this) {
        val mutex = mutexMap[key] ?: return@synchronized
        if(lockedMutexCount[mutex] == 0)
            mutexMap.remove(key)
    }
}

fun <T> FlowParallelLauncher<T>.launchEachSafely (
    coroutineScope: CoroutineScope,
    throwableHandler: suspend (Throwable) -> Unit = { throw it },
    mutexKey: suspend (T) -> Any? = { null },
    consumer: suspend (T) -> Unit
) = launchEach(coroutineScope, mutexKey) {
    try { consumer(it) } catch (t: Throwable) { throwableHandler(t) }
}

fun <T> Flow<T>.createParallelLauncher() = FlowParallelLauncher(flow = this)
