package me.y9san9.prizebot.actors.storage.giveaways_storage

import org.jetbrains.exposed.sql.Database


interface GiveawaysStorage {
    fun getGiveawayById(id: Long): Giveaway?
    fun saveGiveaway(ownerId: Long, title: String, participateButton: String, languageCode: String?): Giveaway
    fun finishGiveaway(giveawayId: Long, winnerId: Long)
    fun getUserGiveaways(ownerId: Long, count: Int = 20, offset: Long = 0): List<Giveaway>
    fun deleteGiveaway(id: Long)
}

@Suppress("FunctionName")
fun GiveawayStorage(database: Database?): GiveawaysStorage =
    if(database == null) KDSGiveawaysStorage() else TableGiveawaysStorage(database)

