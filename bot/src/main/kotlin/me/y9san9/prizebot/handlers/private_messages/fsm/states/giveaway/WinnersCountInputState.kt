package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.giveaway.CreateGiveawayActor
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
data class WinnersCountData (
    val title: String,
    val participateText: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val raffleDate: OffsetDateTime?
)

object WinnersCountInputState : PrizebotFSMState<WinnersCountData> {
    override suspend fun process (
        data: WinnersCountData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {

        suspend fun createGiveaway(winnersCount: WinnersCount = WinnersCount(1)) =
            CreateGiveawayActor.create (
                event, data.title,
                data.participateText, data.raffleDate,
                winnersCount
            )

        event.textOrDefault { text ->
            when(text) {
                "/cancel" -> return MainState.cancellation(event)
                "/skip" -> return createGiveaway()
            }

            val winnersCount = try {
                WinnersCount (
                    text.toIntOrNull()
                        ?: return@textOrDefault event.sendMessage(event.locale.enterNumber).unit
                )
            } catch (_: IllegalArgumentException) {
                return@textOrDefault event.sendMessage(event.locale.winnersCountIsOutOfRange).unit
            }

            return createGiveaway(winnersCount)
        }

        return stateResult(WinnersCountInputState, data)
    }
}

@Suppress("FunctionName")
suspend fun WinnersCountInputState (
    update: PrizebotPrivateMessageUpdate,
    data: WinnersCountData
) = stateResult(WinnersCountInputState, data) {
    update.sendMessage (
        update.locale.enterWinnersCount
    )
}
