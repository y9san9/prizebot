package me.y9san9.prizebot.actors.storage.participants_storage

import org.jetbrains.exposed.sql.Database


interface ParticipantsStorage {
    fun saveParticipant(giveawayId: Long, userId: Long)
    fun getParticipantsIds(giveawayId: Long): List<Long>
    fun getParticipantsCount(giveawayId: Long): Int
    fun isParticipant(giveawayId: Long, userId: Long): Boolean
}

fun ParticipantsStorage(database: Database?): ParticipantsStorage =
    if(database == null)
        KDSParticipantsStorage()
    else
        TableParticipantsStorage(database)
