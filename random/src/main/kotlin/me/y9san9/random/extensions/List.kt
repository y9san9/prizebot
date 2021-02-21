package me.y9san9.random.extensions

import me.y9san9.random.Random


suspend fun <T> List<T>.shuffledRandomOrg() = Random.shuffled(list = this)
