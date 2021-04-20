package me.y9san9.prizebot.extensions.list


inline fun <T> List<T>.plusIf(condition: Boolean, item: () -> T) = if(condition) this + item() else this
