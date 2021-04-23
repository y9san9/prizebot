package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.extensions.any.unit
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import org.intellij.lang.annotations.Language
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset


object CustomTimezoneInputState : PrizebotFSMState<TimezoneInputData> {
    override suspend fun process (
        data: TimezoneInputData,
        event: PrizebotMessageUpdate
    ): FSMStateResult<*> {
        event.textOrDefault { offset ->
            if(offset == "/cancel")
                return MainState.cancellation(event)

            val parsedOffset = parseOffset(offset)
                ?: return@textOrDefault event.sendMessage(event.locale.invalidTimezoneFormat).unit

            val date = OffsetDateTime.of(LocalDateTime.parse(data.localDate, dateTimeFormatter), parsedOffset)

            return WinnersCountInputState (
                event, WinnersCountInputData(data.title, data.participateText, date)
            )
        }

        return stateResult(CustomTimezoneInputState, data)
    }

    @Language("RegExp") private val offsetPattern = "([+-]\\d+)(?::(\\d+))?"

    private fun parseOffset(offset: String): ZoneOffset? {
        val result = Regex(offsetPattern).find(offset)?.groupValues ?: return null
        val hours = result[1].toIntOrNull()?.takeIf { it in -23..23 } ?: return null
        val minutes = result[2].takeIf(String::isNotEmpty)?.let { minutes ->
            minutes.toIntOrNull()?.takeIf { it in 0..59 } ?: return null
        }

        return if(minutes == null)
            ZoneOffset.ofHours(hours)
        else
            ZoneOffset.ofHoursMinutes(hours, minutes)
    }
}
