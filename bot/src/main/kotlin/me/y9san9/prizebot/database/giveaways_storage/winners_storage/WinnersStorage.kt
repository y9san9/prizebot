package me.y9san9.prizebot.database.giveaways_storage.winners_storage

import org.jetbrains.exposed.sql.Database


internal fun WinnersStorage(database: Database): WinnersStorage = TableWinnersStorage(database)

internal interface WinnersStorage {
    fun setWinners(giveawayId: Long, winners: List<Long>)
    fun hasWinners(giveawayId: Long): Boolean
    fun getWinners(giveawayId: Long): List<Long>
}
