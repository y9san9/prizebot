package me.y9san9.prizebot.extensions.flow

import kotlinx.coroutines.CoroutineScope
import me.y9san9.extensions.flow.FlowParallelLauncher
import me.y9san9.extensions.flow.launchEachSafely
import me.y9san9.telegram.updates.primitives.FromUserUpdate


fun <T : FromUserUpdate> FlowParallelLauncher<T>.launchEachSafelyByChatId (
    coroutineScope: CoroutineScope,
    throwableHandler: suspend (Throwable) -> Unit = { throw it },
    consumer: suspend (T) -> Unit
) = launchEachSafely(coroutineScope, throwableHandler, { it.userId }, consumer)
