package me.y9san9.prizebot.extensions.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

val Long.debugDate: String get() {
    val date = Date(this)
    val formatter: DateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}
