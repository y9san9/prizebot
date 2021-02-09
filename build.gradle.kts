import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "me.y9san9.prizebot"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven(url = "https://dl.bintray.com/y9san9/kotlingang")
    }
}

dependencies {
    implementation(project(":telegram"))

    implementation("org.slf4j:slf4j-simple:1.6.1")

    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")

    implementation("dev.inmo:tgbotapi:0.32.3")
    implementation("org.postgresql:postgresql:42.2.18")

    implementation("com.kotlingang.kds:kds:1.2.11")
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
