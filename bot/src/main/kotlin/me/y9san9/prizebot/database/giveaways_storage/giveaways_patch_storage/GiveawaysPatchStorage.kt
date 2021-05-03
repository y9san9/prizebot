package me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage

import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import org.jetbrains.exposed.sql.Database


internal fun GiveawaysPatchStorage(database: Database, winnersStorage: WinnersStorage): GiveawaysPatchStorage =
    TableGiveawaysPatchStorage(database, winnersStorage)


internal interface GiveawaysPatchStorage {
    fun finishGiveaway(giveawayId: Long, winnerIds: List<Long>)
    fun removeRaffleDate(giveawayId: Long)
    fun deleteGiveaway(id: Long)
}