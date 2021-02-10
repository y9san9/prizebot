@file:Suppress("ObjectPropertyName")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


val DependencyHandler.`telegram-extensions` get() = project(":telegram")
val DependencyHandler.fsm get() = project(":fsm")
