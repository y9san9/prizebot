plugins {
    kotlin(plugin.jvm)
    kotlin(plugin.serialization)
}

dependencies {
    implementation(jsonRPC)

    implementation(serialization)

    implementation(ktorCore)
    implementation(ktorCio)
    implementation(ktorSerialization)

    implementation(coroutines)
}
