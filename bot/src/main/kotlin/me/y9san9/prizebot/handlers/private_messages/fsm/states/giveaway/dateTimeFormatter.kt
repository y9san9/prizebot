package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField


val dateTimeFormatter: DateTimeFormatter get() = run {
    val currentDate = LocalDateTime.now()
    return@run DateTimeFormatterBuilder().appendPattern("HH:mm[ dd.MM[.yyyy]]")
        .parseDefaulting(ChronoField.DAY_OF_MONTH, currentDate.getLong(ChronoField.DAY_OF_MONTH))
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, currentDate.getLong(ChronoField.MONTH_OF_YEAR))
        .parseDefaulting(ChronoField.YEAR_OF_ERA, currentDate.getLong(ChronoField.YEAR_OF_ERA))
        .toFormatter()
}
