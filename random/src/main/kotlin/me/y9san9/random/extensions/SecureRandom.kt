package me.y9san9.random.extensions

import java.security.SecureRandom


internal fun <T> SecureRandom.shuffled(list: List<T>): List<T> {
    val mutable = list.toMutableList()

    return List(list.size) { mutable.removeAt(nextInt(mutable.size)) }
}
