import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin(plugin.jvm)
}

kotlin {
    jvmToolchain(17)
    this.compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(coroutines)
    implementation(tgBotApi)
    implementation(aqueue)
}
