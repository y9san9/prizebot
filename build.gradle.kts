import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import me.y9san9.deploy.Deploy
import me.y9san9.deploy.DeployConfiguration
import me.y9san9.deploy.ssh
import org.jetbrains.kotlin.konan.properties.loadProperties


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

val deployPropertiesFile = file("deploy.properties")

if(deployPropertiesFile.exists()) {
    val properties = loadProperties(deployPropertiesFile.absolutePath)

    project.apply<Deploy>()
    project.configure<DeployConfiguration> {
        serviceName = "prizebot"
        implementationTitle = "prizebot"
        mainClassName = "me.y9san9.prizebot.MainKt"
        host = properties.getProperty("host")
        user = properties.getProperty("user")
        password = properties.getProperty("password")
        deployPath = properties.getProperty("deployPath")
    }

    task("stop") {
        group = "deploy"

        doLast {
            project.ssh {
                execute("systemctl stop ${project.the<DeployConfiguration>().serviceName}")
            }
        }
    }
}
