package me.y9san9.prizebot.database.giveaways_storage.conditions_storage

import org.jetbrains.exposed.sql.Database


internal fun ConditionsStorage(database: Database): ConditionsStorage = TableConditionsStorage(database)

internal interface ConditionsStorage {
    fun addConditions(giveawayId: Long, conditions: GiveawayConditions)
    fun loadConditions(giveawayId: Long): GiveawayConditions
}
