@file:Suppress("ObjectPropertyName")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


val DependencyHandler.telegram get() = project(":bot:telegram")
val DependencyHandler.fsm get() = project(":fsm")
val DependencyHandler.bot get() = project(":bot")
val DependencyHandler.random get() = project(":random")
val DependencyHandler.jsonRPC get() = project(":random:jsonRPC")
val DependencyHandler.utils get() = project(":utils")
val DependencyHandler.`db-migrations` get() = project(":db-migrations")
val DependencyHandler.conditions get() = project(":conditions")
