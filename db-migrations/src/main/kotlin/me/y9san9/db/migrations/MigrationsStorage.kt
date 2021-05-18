package me.y9san9.db.migrations

import me.y9san9.extensions.any.unit
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


internal class MigrationsStorage (
    private val database: Database,
    tableName: String
) {
    private val table = object : Table(tableName) {
        val schemaVersion = integer(name = "schemaVersion")
    }

    init {
        transaction(database) {
            SchemaUtils.create(table)
        }
    }

    fun getVersion(): Int? = transaction(database) {
        table.selectAll().singleOrNull()?.get(table.schemaVersion)
    }

    fun setVersion(value: Int) = transaction(database) {
        table.deleteAll()
        table.insert {
            it[schemaVersion] = value
        }
    }.unit
}
