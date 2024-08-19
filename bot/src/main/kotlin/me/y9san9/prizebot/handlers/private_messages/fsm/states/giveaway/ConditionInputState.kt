package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.actors.giveaway.CreateGiveawayActor
import me.y9san9.prizebot.database.giveaways_storage.WinnersCount
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.*
import me.y9san9.extensions.offset_date_time.OffsetDateTimeSerializer
import me.y9san9.prizebot.database.giveaways_storage.WinnersSettings
import me.y9san9.prizebot.extensions.telegram.*
import me.y9san9.prizebot.handlers.private_messages.fsm.states.MainState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.InvitationsCountInputState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.SubscriptionChannelInputState
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.prizebot.resources.markups.conditionsMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import java.time.OffsetDateTime


@SerialName("condition_input")
@Serializable
data class ConditionInputData (
    val title: String,
    val participateText: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val raffleDate: OffsetDateTime?,
    val winnersSettings: WinnersSettings,
    val conditions: List<Condition> = listOf()
)

object ConditionInputState : PrizebotFSMState<ConditionInputData> {
    override suspend fun process (
        data: ConditionInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {
        event.commandOrDefault {
            case("/cancel") {
                return MainState.cancellation(event)
            }
            case("/next") {
                when(val conditions = GiveawayConditions.createChecked(data.conditions)) {
                    is ChannelConditionRequiredForInvitations ->
                        event.sendMessage(event.getLocale().channelConditionRequiredForInvitations)
                    is OnlyOneInvitationConditionAllowed -> error("Checked before")
                    is GiveawayConditions -> return CreateGiveawayActor.create (
                        event, data.title,
                        data.participateText, data.raffleDate,
                        data.winnersSettings, conditions
                    )
                }
            }
            raw(Locale::invitations) {
                if(data.conditions.count { it is Condition.Invitations } > 0)
                    event.sendMessage (
                        text = event.getLocale().youHaveAlreadyAddedInvitations,
                        replyMarkup = conditionsMarkup(event)
                    )
                else
                    return InvitationsCountInputState(event, data)
            }
            raw(Locale::channelSubscription) {
                return SubscriptionChannelInputState(event, data)
            }
        }

        return stateResult(ConditionInputState, data)
    }
}


@Suppress("FunctionName")
suspend fun ConditionInputState (
    update: PrizebotPrivateMessageUpdate,
    data: ConditionInputData,
    text: String? = null
) = stateResult(ConditionInputState, data) {
    update.sendMessage(
        text = text ?: update.getLocale().chooseConditions,
        replyMarkup = conditionsMarkup(update)
    )
}
