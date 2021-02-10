plugins {
    kotlin(plugin.jvm)
}

dependencies {
    api(fsm)

    implementation(coroutines)
    implementation(tgBotApi)
}
