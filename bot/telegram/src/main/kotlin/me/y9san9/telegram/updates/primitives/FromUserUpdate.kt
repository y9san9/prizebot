package me.y9san9.telegram.updates.primitives


interface FromUserUpdate : PossiblyFromUserUpdate {
    override val userId: Long
}
