package me.y9san9.prizebot.actors.storage.participants_storage

import kotlinx.serialization.Serializable


@Serializable
data class Participant (
    val giveawayId: Long,
    val userId: Long
)
