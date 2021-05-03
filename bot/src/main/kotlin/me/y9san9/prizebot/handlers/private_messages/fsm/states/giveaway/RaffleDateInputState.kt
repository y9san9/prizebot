package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import java.time.LocalDateTime
import java.time.format.DateTimeParseException


@SerialName("raffle_date_input")
@Serializable
data class RaffleDateInputData (
    val title: String,
    val participateText: String
)

object RaffleDateInputState : PrizebotFSMState<RaffleDateInputData> {
    override suspend fun process (
        data: RaffleDateInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {
        event.textOrDefault { text ->
            return when(text) {
                "/cancel" -> MainState.cancellation(event)
                "/skip" -> nextState(event, data, raffleDate = null)
                else -> nextState(event, data, text.trim())
            }
        }

        return stateResult(RaffleDateInputState, data)
    }

    private suspend fun nextState (
        update: PrizebotPrivateMessageUpdate,
        data: RaffleDateInputData,
        raffleDate: String?
    ): FSMStateResult<*> {
        if(raffleDate == null)
            return WinnersCountInputState (
                update,
                WinnersCountInputData(data.title, data.participateText, raffleDate = null)
            )
        else if(!isDateValid(raffleDate))
            return stateResult(RaffleDateInputState, data) {
                update.sendMessage(update.locale.invalidDateFormat)
            }

        return TimezoneInputState(update, TimezoneInputData(data.title, data.participateText, raffleDate))
    }

    private fun isDateValid(date: String): Boolean {
        try {
            LocalDateTime.parse(date, dateTimeFormatter)
            return true
        } catch (t: DateTimeParseException) { }

        return false
    }
}
