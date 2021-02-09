plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":fsm"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("dev.inmo:tgbotapi:0.32.1")
}
