package me.y9san9.telegram.updates.primitives


interface AnswerableUpdate {
    suspend fun answer()
}
