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
    implementation(tgBotApi)
    implementation(`telegram-extensions`)

    implementation(exposed)
    implementation(exposedJdbc)
    compileOnly(postgresql)  // default jdbc driver dependency; replace with your own if you want.

    implementation(slf4j)

    implementation(kds)
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Version.JVM
    }
}
