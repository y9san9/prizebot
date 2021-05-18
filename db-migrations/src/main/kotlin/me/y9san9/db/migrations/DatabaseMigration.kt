package me.y9san9.db.migrations

import org.jetbrains.exposed.sql.Database


interface DatabaseMigration {
    val applyVersion: Int

    /**
     * @contract should always be more than [applyVersion]
     */
    val afterVersion get() = applyVersion + 1

    fun migrate(database: Database)
}
