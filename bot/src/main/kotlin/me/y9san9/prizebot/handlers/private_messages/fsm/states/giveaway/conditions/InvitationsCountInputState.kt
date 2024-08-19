package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.PositiveInt
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.PositiveIntRequired
import me.y9san9.extensions.any.unit
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.ConditionInputData
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.ConditionInputState
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


object InvitationsCountInputState : PrizebotFSMState<ConditionInputData> {
    override suspend fun process (
        data: ConditionInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {
        event.textOrDefault { text ->
            val number = text.toIntOrNull()
                ?: return@textOrDefault event.sendMessage(event.getLocale().enterNumber).unit

            when(val positiveInt = PositiveInt.createChecked(number)) {
                is PositiveIntRequired -> event.sendMessage(event.getLocale().invitationsCountShouldBePositive)
                is PositiveInt -> {
                    val conditions = data.conditions + Condition.Invitations(positiveInt)
                    return ConditionInputState(
                        event,
                        data.copy(conditions = conditions),
                        event.getLocale().chooseMoreConditions
                    )
                }
            }
        }

        return stateResult(InvitationsCountInputState, data)
    }
}


@Suppress("FunctionName")
suspend fun InvitationsCountInputState (
    update: PrizebotPrivateMessageUpdate, data: ConditionInputData
) = stateResult(InvitationsCountInputState, data) {
    update.sendMessage(update.getLocale().enterInvitationsCount, replyMarkup = ReplyKeyboardRemove())
}
