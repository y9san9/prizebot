@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.random

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import me.y9san9.random.extensions.shuffled
import java.security.SecureRandom


object Random {
    private val randomOrgApiKey: String = System.getenv("RANDOM_ORG_API_KEY")

    /**
     * Random numbers from 0 to 1_000_000_000
     */
    val randomSeeds = flow {
        while (true) {
            RandomOrgAPI
                .getRandomIntegers(apiKey = randomOrgApiKey, min = 0, max = 1_000_000_000)
                .forEach { emit(it) }
        }
    }

    /**
     * One-time secure randoms based on seed from random.org,
     * so it is still safe and powered by random.org
     */
    val secureRandoms = flow {
        randomSeeds.collect { seed ->
            val random = SecureRandom()
            random.setSeed(seed.toLong())
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
}
