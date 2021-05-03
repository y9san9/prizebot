package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.extensions.offset_date_time.OffsetDateTimeSerializer
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.database.giveaways_storage.WinnersSettings
import me.y9san9.prizebot.extensions.telegram.*
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.prizebot.resources.markups.yesNoMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import java.time.OffsetDateTime


@Serializable
data class DisplayWinnersWithEmojisInputData (
    val title: String,
    val participateText: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val raffleDate: OffsetDateTime?,
    val winnersCount: WinnersCount
)

object DisplayWinnersWithEmojisInputState : PrizebotFSMState<DisplayWinnersWithEmojisInputData> {
    override suspend fun process (
        data: DisplayWinnersWithEmojisInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {
        event.commandOrDefault {
            raw(Locale::yes) {
                return ConditionInputState (
                    event, ConditionInputData (
                        data.title, data.participateText, data.raffleDate,
                        WinnersSettings.create(data.winnersCount, displayWithEmojis = true)
                    )
                )
            }
            raw(Locale::no) {
                return ConditionInputState (
                    event, ConditionInputData (
                        data.title, data.participateText, data.raffleDate,
                        WinnersSettings.create(data.winnersCount, displayWithEmojis = false)
                    )
                )
            }
        }

        return DisplayWinnersWithEmojisInputData(event, data)
    }
}


@Suppress("FunctionName")
suspend fun DisplayWinnersWithEmojisInputData (
    update: PrizebotPrivateMessageUpdate,
    data: DisplayWinnersWithEmojisInputData
) = stateResult(DisplayWinnersWithEmojisInputState, data) {
    update.sendMessage(update.locale.displayWinnersWithEmoji, replyMarkup = yesNoMarkup(update.locale))
}
