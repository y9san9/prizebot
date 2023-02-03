plugins {
    kotlin(plugin.jvm)
    kotlin(plugin.serialization)
}

dependencies {
    implementation(coroutines)

    implementation(exposed)
    implementation(exposedJdbc)
    implementation(databaseDriver)  // default jdbc driver dependency; replace with your own if you want.

    implementation(slf4j)

    implementation(fsm)
    implementation(`db-migrations`)
    implementation(telegram)
    implementation(random)
    implementation(utils)
    implementation(conditions)
}
