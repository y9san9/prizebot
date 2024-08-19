package me.y9san9.prizebot.database.giveaways_storage.winners_storage

import org.jetbrains.exposed.sql.Database


internal fun WinnersStorage(database: Database): WinnersStorage = TableWinnersStorage(database)

internal interface WinnersStorage {
    suspend fun setWinners(giveawayId: Long, winners: List<Long>)
    suspend fun hasWinners(giveawayId: Long): Boolean
    suspend fun getWinners(giveawayId: Long): List<Long>
}
