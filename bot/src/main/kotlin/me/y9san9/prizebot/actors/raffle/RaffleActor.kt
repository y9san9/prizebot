package me.y9san9.prizebot.actors.raffle

import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage


object RaffleActor {
    inline fun raffle (
        giveawayId: Long,
        storage: ParticipantsStorage,
        filter: (Long) -> Boolean = { true }  // for now that is not used yet, but will to filter users that are not participating
    ) = storage
        .getParticipantsIds(giveawayId)
        .shuffled()
        .firstOrNull(filter)
}
