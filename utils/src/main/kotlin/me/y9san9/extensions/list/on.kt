package me.y9san9.extensions.list


inline fun <reified T, R> List<R>.on(handler: (T) -> Unit) = onEach {
    if(it is T)
        handler(it)
}
