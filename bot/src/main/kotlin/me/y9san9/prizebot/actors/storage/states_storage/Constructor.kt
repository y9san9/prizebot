package me.y9san9.prizebot.actors.storage.states_storage

import kotlinx.serialization.modules.SerializersModule
import me.y9san9.fsm.FSMStorage
import me.y9san9.fsm.storage.KDSStorage
import org.jetbrains.exposed.sql.Database


typealias PrizebotFSMStorage = FSMStorage<Long, Any?>

fun PrizebotFSMStorage(database: Database, statesSerializers: SerializersModule): PrizebotFSMStorage =
    TableStorage(database, statesSerializers)
