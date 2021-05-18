# DB Migrations
> **Note**: This is module with migrations implementation, for actual migrations see [it](https://github.com/y9san9/prizebot/tree/dev/bot/src/main/kotlin/me/y9san9/prizebot/database/migrations)

## Structure

The migrations are designed with [MigrationsApplier](https://github.com/y9san9/prizebot/blob/dev/db-migrations/src/main/kotlin/me/y9san9/db/migrations/MigrationsApplier.kt) as main entity. It performs all migrations in recursion-way (because personally I hate cycles and mutable state)

[MigrationsStorage](https://github.com/y9san9/prizebot/blob/dev/db-migrations/src/main/kotlin/me/y9san9/db/migrations/MigrationsStorage.kt) is a simple set-get table model for tracking schema version
