package me.y9san9.db.migrations

import org.jetbrains.exposed.sql.SchemaUtils


class MigrationsConfig (
    // Table name for tracking versions in database
    var tableName: String = "migrations",
    /**
     * if null, then by default module will assume that the table is already up-to-date
     * Example: you have just deployed your new bot instance and all tables were created
     * by [SchemaUtils.create], so there is no need in any migrations and it will take one
     * from latest [DatabaseMigration.afterVersion] or will keep it null if there is no migrations yet
     */
    var defaultVersion: Int? = null
)
