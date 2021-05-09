package me.y9san9.telegram.updates.primitives


interface DeletableUpdate {
    suspend fun delete()
}
