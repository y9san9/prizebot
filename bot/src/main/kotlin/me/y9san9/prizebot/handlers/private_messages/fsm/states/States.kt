package me.y9san9.prizebot.handlers.private_messages.fsm.states

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import me.y9san9.fsm.statesOf
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.*
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.InvitationsCountInputState
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.SubscriptionChannelInputData
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions.SubscriptionChannelInputState


val prizebotStates = statesOf (
    initial = MainState,
    TitleInputState, ParticipateTextInputState,
    RaffleDateInputState, TimezoneInputState,
    CustomTimezoneInputState, WinnersCountInputState,
    ConditionInputState, InvitationsCountInputState,
    SubscriptionChannelInputState, DisplayWinnersWithEmojisInputState
)

val statesSerializers = SerializersModule {
    contextual(Long::class, Long.serializer())

    polymorphic(Any::class) {
        subclass(Unit::class, Unit.serializer())
        subclass(ParticipateTextInputData::class, ParticipateTextInputData.serializer())
        subclass(RaffleDateInputData::class, RaffleDateInputData.serializer())
        subclass(TimezoneInputData::class, TimezoneInputData.serializer())
        subclass(WinnersCountInputData::class, WinnersCountInputData.serializer())
        subclass(ConditionInputData::class, ConditionInputData.serializer())
        subclass(SubscriptionChannelInputData::class, SubscriptionChannelInputData.serializer())
        subclass(DisplayWinnersWithEmojisInputData::class, DisplayWinnersWithEmojisInputData.serializer())
    }
}
