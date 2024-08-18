plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
}

dependencies {
    implementation("org.hidetake:gradle-ssh-plugin:2.10.1")
}
