package me.y9san9.prizebot.bot.states

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
import me.y9san9.prizebot.bot.states.giveaway.GiveawayTitle
import kotlin.reflect.KClass


val statesSerializers = SerializersModule {
    contextual(Long::class, Long.serializer())

    polymorphic(Any::class) {
        subclass(Unit::class, Unit.serializer())
        subclass(GiveawayTitle::class, GiveawayTitle.serializer())
    }
}
