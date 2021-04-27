plugins {
    kotlin(plugin.jvm)
    kotlin(plugin.serialization)
}

dependencies {
    implementation(utils)
    implementation(coroutines)
    implementation(serialization)
}
