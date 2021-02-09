package me.y9san9.prizebot.logic.utils


fun String.awesomeCut(maxLength: Int, postfix: String = "…"): String {
    val result = take(maxLength)

    return if(result.length != length) result + postfix else result
}
