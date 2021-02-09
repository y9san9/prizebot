package me.y9san9.prizebot.logic.utils

import java.security.SecureRandom


val random = SecureRandom(byteArrayOf(0, 100, -128))

fun <T> List<T>.secureRandom() = random
    .nextInt(size)
    .let { this[it] }
