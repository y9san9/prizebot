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
    implementation(kds)
}
