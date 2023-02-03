package me.y9san9.extensions.job

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.completeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import me.y9san9.extensions.flow.parallelEach

class JobLauncher(
    private val scope: CoroutineScope
) {
    private val blocks = MutableSharedFlow<Pair<Any?, suspend () -> Unit>>()

    init {
        blocks.parallelEach(
            scope,
            consistentKey = { (key) -> key },
            consumer = { (_, block) -> block() }
        )
    }

    fun <R> async(key: Any?, block: suspend () -> R): Deferred<R> = CompletableDeferred<R>().apply {
        scope.launch {
            blocks.emit(
                value = key to {
                    val result = runCatching { block() }
                    completeWith(result)
                }
            )
        }
    }

    fun launch(key: Any?, block: suspend () -> Unit): Job = async(key, block)
}
