@file:Suppress("ClassName")

package me.y9san9.prizebot.database.migrations

import me.y9san9.db.migrations.DatabaseMigration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object `Migration 1-2` : DatabaseMigration {
    override val applyVersion = 1

    override fun migrate(database: Database) {
        with (Table("giveaways_active_messages")) {
            transaction(database) {
                val column = long("lastUpdateTime")
                // first putting default time as current
                execInBatch(
                    column.default(System.currentTimeMillis()).createStatement()
                )
                // then removing default time, it's better to crash
                // after migration if for some reason the time will
                // not be provided to database
                execInBatch(
                    column.modifyStatement()
                )
            }
        }
    }
}
