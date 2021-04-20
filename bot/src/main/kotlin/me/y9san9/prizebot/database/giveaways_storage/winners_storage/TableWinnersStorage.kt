package me.y9san9.prizebot.database.giveaways_storage.winners_storage

import me.y9san9.prizebot.database.giveaways_storage.winners_storage.TableWinnersStorage.Winners.GIVEAWAY_ID
import me.y9san9.prizebot.database.giveaways_storage.winners_storage.TableWinnersStorage.Winners.WINNER_ID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableWinnersStorage(private val database: Database) : WinnersStorage {
    private object Winners : Table(name = "winners") {
        val GIVEAWAY_ID = long("giveawayId")
        val WINNER_ID = long("winnerId")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Winners)
        }
    }

    override fun setWinners(giveawayId: Long, winners: List<Long>) = transaction(database) {
        for(winner in winners) {
            Winners.insert {
                it[GIVEAWAY_ID] = giveawayId
                it[WINNER_ID] = winner
            }
        }
    }

    override fun hasWinners(giveawayId: Long): Boolean = transaction(database) {
        Winners.select { GIVEAWAY_ID eq giveawayId }
            .firstOrNull() == null
    }

    override fun getWinners(giveawayId: Long): List<Long> = transaction(database) {
        Winners.select { GIVEAWAY_ID eq giveawayId }
            .map { it[WINNER_ID] }
    }

}
