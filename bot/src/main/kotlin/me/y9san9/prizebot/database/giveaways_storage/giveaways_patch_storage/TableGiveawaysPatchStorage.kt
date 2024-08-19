package me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage

import kotlinx.coroutines.Dispatchers
import me.y9san9.prizebot.database.giveaways_storage.Giveaways
import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


internal class TableGiveawaysPatchStorage (
    private val database: Database,
    private val winnersStorage: WinnersStorage
) : GiveawaysPatchStorage {

    init {
        transaction(database) {
            SchemaUtils.create(Giveaways)
        }
    }

    override suspend fun removeRaffleDate(giveawayId: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Giveaways.update({ Giveaways.GIVEAWAY_ID eq giveawayId }) {
            it[GIVEAWAY_RAFFLE_DATE] = null
        }
    }.unit

    override suspend fun finishGiveaway(giveawayId: Long, winnerIds: List<Long>) {
        winnersStorage.setWinners(giveawayId, winnerIds)
    }

    override suspend fun deleteGiveaway(id: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Giveaways.deleteWhere { Giveaways.GIVEAWAY_ID eq id }
    }.unit
}
