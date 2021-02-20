import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin(plugin.jvm) version Version.KOTLIN
    kotlin(plugin.serialization) version Version.SERIALIZATION_PLUGIN
}

group = AppInfo.PACKAGE
version = AppInfo.VERSION

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven(url = "https://dl.bintray.com/y9san9/kotlingang")
    }
}

dependencies {
    implementation(bot)
    implementation(coroutines)
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Version.JVM
    }
}
