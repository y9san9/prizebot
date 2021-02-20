package me.y9san9.prizebot.logic.utils


fun <T> MutableList<T>.replaceFirst(predicate: (T) -> Boolean, transformer: (T) -> (T)) {
    val index = indexOfFirst(predicate).takeIf { it != -1 } ?: return
    this[index] = transformer(this[index])
}

inline fun <T> List<T>.plusIf(condition: Boolean, item: () -> T) = if(condition) this + item() else this
