package me.y9san9.prizebot.actors.storage.winners_storage

import org.jetbrains.exposed.sql.Database


fun WinnersStorage(database: Database): WinnersStorage = TableWinnersStorage(database)

interface WinnersStorage {
    fun setWinners(giveawayId: Long, winners: List<Long>)
    fun getWinners(giveawayId: Long): List<Long>
}
