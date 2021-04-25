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

    tasks.withType<KotlinCompile>() {
        kotlinOptions {
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs = listOf("-Xinline-classes")
        }
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
        // On linux should be something like /home/user/.ssh/known_hosts
        // Or Default Allow Any Hosts if this value is not specified,
        // But than MITM may be performed
        knownHostsFile = properties.getProperty("knownHosts")
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
