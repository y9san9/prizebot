package me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage

import me.y9san9.prizebot.database.giveaways_storage.Giveaways
import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
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

    override fun removeRaffleDate(giveawayId: Long) = transaction(database) {
        Giveaways.update({ Giveaways.GIVEAWAY_ID eq giveawayId }) {
            it[GIVEAWAY_RAFFLE_DATE] = null
        }
    }.unit

    override fun finishGiveaway(giveawayId: Long, winnerIds: List<Long>) {
        winnersStorage.setWinners(giveawayId, winnerIds)
    }

    override fun deleteGiveaway(id: Long) = transaction(database) {
        Giveaways.deleteWhere { Giveaways.GIVEAWAY_ID eq id }
    }.unit
}
