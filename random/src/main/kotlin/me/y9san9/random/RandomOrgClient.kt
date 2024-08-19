@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.random

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.y9san9.random.extensions.shuffled
import java.security.SecureRandom


class RandomOrgClient(
    private val apiKey: String,
    scope: CoroutineScope
) {
    private val _randomSeeds = Channel<Int>()
    val randomSeeds: ReceiveChannel<Int> = _randomSeeds

    init {
        scope.launch {
            var retries = 0
            while (true) {
                val randomIntegers = RandomOrgAPI.getRandomIntegers(apiKey = apiKey, min = 0, max = 1_000_000_000).getOrNull()
                if (randomIntegers == null) {
                    delay(5_000)
                    System.err.println("> RandomOrgClient: Cannot get random seed for secure random ${++retries} times")
                    if (retries > 10) {
                        val secureRandomBackup = SecureRandom()
                        _randomSeeds.send(element = secureRandomBackup.nextInt(0, 1_000_000_000))
                        System.err.println("> RandomOrgClient: BACKUP SECURE RANDOM WAS USED. This must be fixed soon, because bot must rely on random.org")
                    }
                    continue
                }
                retries = 0
                for (integer in randomIntegers) {
                    _randomSeeds.send(integer)
                }
            }
        }
    }

    /**
     * One-time secure randoms based on seed from random.org,
     * so it is still safe and powered by random.org
     */
    val secureRandoms = flow {
        randomSeeds.receiveAsFlow().collect { seedInt ->
            val seed = byteArrayOf(
                (seedInt shr 0).toByte(),
                (seedInt shr 8).toByte(),
                (seedInt shr 16).toByte(),
                (seedInt shr 24).toByte(),
            )
            val random = SecureRandom(seed)
            emit(random)
        }
    }

    suspend fun <T> shuffled(list: List<T>): List<T> {
        // If there is only one element, return list copy
        if(list.size <= 1)
            return list.toList()

        val random = secureRandoms.first()

        return random.shuffled(list)
    }

    companion object {
        const val RETRY_DELAY = 5_000
    }
}
