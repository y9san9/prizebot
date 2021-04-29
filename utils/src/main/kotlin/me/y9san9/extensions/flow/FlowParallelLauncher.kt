package me.y9san9.extensions.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.y9san9.extensions.any.unit


suspend fun main() = coroutineScope {
    MutableSharedFlow<Int>(replay = 1)
        .also {
            launch {
                delay(1000)
                for (i in 1..1000)
                    it.emit(i)
            }
        }
        .createParallelLauncher()
        .launchEach(coroutineScope = this) {}
}.unit

/**
 * Main use-case of this class is paralleling user events:
 *  events from different users should be parallel while
 *  events from one user should come consistently
 */
class FlowParallelLauncher<T> (
    private val flow: Flow<T>,
) {
    private val mapMutex = Mutex()
    private val consumersCount = mutableMapOf<Any, Int>()
    private val sharedFlows = mutableMapOf<Any, MutableSharedFlow<T>>()

    fun launchEach (
        coroutineScope: CoroutineScope,
        /**
         * @return null if there is no need in consistence
         */
        consistentKey: suspend (T) -> Any? = { null },
        consumer: suspend (T) -> Unit
    ) = flow.onEach { item ->
        val key = consistentKey(item)
            ?: return@onEach coroutineScope.launch { consumer(item) }.unit

        val flow = mapMutex.withLock {
            consumersCount.compute(key) { _, count -> count?.inc() ?: 0 }
            sharedFlows.getOrPut(key) {
                val flow = MutableSharedFlow<T>(replay = 1)

                flow.onEach { item ->
                    consumer(item)
                    mapMutex.withLock {
                        consumersCount.compute(key) { _, count -> count?.dec() ?: 0 }
                        if(consumersCount[key] == 0) {
                            sharedFlows.remove(key)
                            consumersCount.remove(key)
                        }
                    }
                }.launchIn(coroutineScope)
                return@getOrPut flow
            }
        }
        coroutineScope.launch {
            flow.emit(item)
        }
    }.launchIn(coroutineScope)
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
