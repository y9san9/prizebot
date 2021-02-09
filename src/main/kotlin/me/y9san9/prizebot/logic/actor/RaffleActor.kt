package me.y9san9.prizebot.logic.actor

import me.y9san9.prizebot.logic.database.ParticipantsStorage


object RaffleActor {
    inline fun raffle (
        giveawayId: Long,
        storage: ParticipantsStorage,
        filter: (Long) -> Boolean = { true }
    ) = storage
        .getParticipantsIds(giveawayId)
        .shuffled()
        .firstOrNull(filter)

    // TODO: Add filter conditions in future
}
