package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.giveaway.CreateGiveawayActor
import me.y9san9.prizebot.actors.storage.giveaways_storage.WinnersCount
import me.y9san9.prizebot.extensions.any.unit
import me.y9san9.prizebot.extensions.offset_date_time.OffsetDateTimeSerializer
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
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

        event.textOrDefault {
            val winnersCount = try {
                WinnersCount (
                    it.toIntOrNull() ?: return@textOrDefault event.sendMessage(event.locale.enterNumber).unit
                )
            } catch (_: IllegalArgumentException) {
                return@textOrDefault event.sendMessage(event.locale.winnersCountIsOutOfRange).unit
            }

            return CreateGiveawayActor.create (
                event, data.title,
                data.participateText, data.raffleDate,
                winnersCount
            )
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
