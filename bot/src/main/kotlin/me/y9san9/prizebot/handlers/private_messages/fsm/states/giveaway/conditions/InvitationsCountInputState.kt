package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions

import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardRemove
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.wrapPositiveInt
import me.y9san9.prizebot.extensions.any.unit
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
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
                ?: return@textOrDefault event.sendMessage(event.locale.enterNumber).unit

            if(number > 0) {
                val conditions = data.conditions + Condition.Invitations(number.wrapPositiveInt())
                return ConditionInputState(
                    event,
                    data.copy(conditions = conditions),
                    event.locale.chooseMoreConditions
                )
            } else
                event.sendMessage(event.locale.invitationsCountShouldBePositive)
        }

        return stateResult(InvitationsCountInputState, data)
    }
}


@Suppress("FunctionName")
suspend fun InvitationsCountInputState (
    update: PrizebotPrivateMessageUpdate, data: ConditionInputData
) = stateResult(InvitationsCountInputState, data) {
    update.sendMessage(update.locale.enterInvitationsCount, replyMarkup = ReplyKeyboardRemove())
}
