plugins {
    kotlin(plugin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(tgBotApi)
}
