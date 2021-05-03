plugins {
    kotlin(plugin.jvm)
    kotlin(plugin.serialization)
}

dependencies {
    implementation(coroutines)
    implementation(serialization)
}
