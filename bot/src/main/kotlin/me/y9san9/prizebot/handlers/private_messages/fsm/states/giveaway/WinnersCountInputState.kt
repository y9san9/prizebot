package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.extensions.any.unit
import me.y9san9.prizebot.extensions.offset_date_time.OffsetDateTimeSerializer
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import java.time.OffsetDateTime


@Serializable
data class WinnersCountInputData (
    val title: String,
    val participateText: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val raffleDate: OffsetDateTime?
)

object WinnersCountInputState : PrizebotFSMState<WinnersCountInputData> {
    override suspend fun process (
        data: WinnersCountInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {

        suspend fun next(winnersCount: WinnersCount = WinnersCount(1)) =
            ConditionInputState (
                event,
                ConditionInputData (
                    data.title, data.participateText,
                    data.raffleDate, winnersCount
                )
            )

        event.textOrDefault { text ->
            when(text) {
                "/cancel" -> return MainState.cancellation(event)
                "/skip" -> return next()
            }

            val winnersCount = try {
                WinnersCount (
                    text.toIntOrNull()
                        ?: return@textOrDefault event.sendMessage(event.locale.enterNumber).unit
                )
            } catch (_: IllegalArgumentException) {
                return@textOrDefault event.sendMessage(event.locale.winnersCountIsOutOfRange).unit
            }

            return next(winnersCount)
        }

        return stateResult(WinnersCountInputState, data)
    }
}

@Suppress("FunctionName")
suspend fun WinnersCountInputState (
    update: PrizebotPrivateMessageUpdate,
    data: WinnersCountInputData
) = stateResult(WinnersCountInputState, data) {
    update.sendMessage (
        update.locale.enterWinnersCount
    )
}
