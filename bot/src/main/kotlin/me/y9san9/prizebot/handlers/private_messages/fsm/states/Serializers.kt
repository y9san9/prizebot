package me.y9san9.prizebot.handlers.private_messages.fsm.states

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.GiveawayTitle
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.RaffleDateInputData
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.TimezoneInputData


val statesSerializers = SerializersModule {
    contextual(Long::class, Long.serializer())

    polymorphic(Any::class) {
        subclass(Unit::class, Unit.serializer())
        subclass(GiveawayTitle::class, GiveawayTitle.serializer())
        subclass(RaffleDateInputData::class, RaffleDateInputData.serializer())
        subclass(TimezoneInputData::class, TimezoneInputData.serializer())
    }
}
