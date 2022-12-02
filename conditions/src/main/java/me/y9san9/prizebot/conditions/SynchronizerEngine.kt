package me.y9san9.prizebot.conditions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface SynchronizerEngine {
    /**
     * If there is a [block] running right now (discriminated by [discriminator]), then
     * only the first should be executed and result should be returned to all invocations.
     *
     * Result SHOULD NOT be cached, but rather block needs to be executed again after identity became inactive.
     */
    suspend fun <R> execute(
        discriminator: Any,
        block: suspend () -> R
    ): R
}

fun SynchronizerEngine(
    scope: CoroutineScope,
    discrimination: suspend (Any, Any) -> Boolean = { first, second -> first == second }
): SynchronizerEngine = DefaultSynchronizer(scope, discrimination)

private class DefaultSynchronizer(
    val scope: CoroutineScope,
    val discrimination: suspend (Any, Any) -> Boolean
) : SynchronizerEngine {
    private val mutex = Mutex()
    private val runningJobs = mutableListOf<RunningJob<*>>()

    override suspend fun <R> execute(
        discriminator: Any,
        block: suspend () -> R
): R {
        mutex.lock()

        val running = runningJobs.firstOrNull { job -> discrimination(discriminator, job.discriminator) }

        if (running != null) {
            mutex.unlock()
            @Suppress("UNCHECKED_CAST")
            return running.deferred.await() as R
        }

        val newRunning = scope.async { block() }

        runningJobs += RunningJob(discriminator, newRunning)

        mutex.unlock()

        val result = newRunning.await()

        mutex.withLock {
            runningJobs.removeAll { it.discriminator == discriminator }
        }

        return result
    }

    class RunningJob<R>(val discriminator: Any, val deferred: Deferred<R>)
}
