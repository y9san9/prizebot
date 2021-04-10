import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin(plugin.jvm)
    kotlin(plugin.serialization)
}

dependencies {
    implementation(coroutines)

    implementation(exposed)
    implementation(exposedJdbc)
    implementation(postgresql)  // default jdbc driver dependency; replace with your own if you want.

    implementation(slf4j)

    implementation(fsm)
    implementation(telegram)
    implementation(random)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}
