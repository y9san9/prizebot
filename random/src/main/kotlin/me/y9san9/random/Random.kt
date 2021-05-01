@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.random

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import me.y9san9.random.extensions.shuffled
import java.security.SecureRandom


object Random {
    /**
     * Random numbers from 0 to 1_000_000_000
     */
    val randomSeeds = flow {
        while(true) {
            RandomOrgAPI
                .getRandomIntegers(min = 0, max = 1_000_000_000)
                .forEach { emit(it) }
        }
    }

    /**
     * One-time secure randoms based on seed from random.org,
     * so it is still safe and powered by random.org
     */
    val secureRandoms = flow {
        randomSeeds.collect {
            emit(SecureRandom(SecureRandom.getSeed(it)))
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
