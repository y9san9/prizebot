package me.y9san9.prizebot.actors.storage.giveaways_active_messages_storage

import kotlinx.serialization.Serializable


@Serializable
class ActiveMessage (
    val giveawayId: Long,
    val messageId: String
)
