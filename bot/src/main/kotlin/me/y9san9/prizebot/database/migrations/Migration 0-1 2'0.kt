@file:Suppress("ClassName")

package me.y9san9.prizebot.database.migrations

import me.y9san9.db.migrations.DatabaseMigration
import org.jetbrains.exposed.sql.Database


object `Migration 0-1 2'0` : DatabaseMigration {
    override val applyVersion = 0

    /**
     * Optional, implicitly [applyVersion] + 1
     */
    override val afterVersion = 1

    // Fake migration
    override fun migrate(database: Database) = Unit
}
