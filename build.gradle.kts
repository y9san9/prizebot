import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import me.y9san9.deploy.Deploy
import me.y9san9.deploy.DeployConfiguration
import me.y9san9.deploy.ssh
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
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
    }
}

dependencies {
    implementation(utils)
    implementation(bot)
    implementation(coroutines)
    implementation(h2Database)
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
        // But then MITM may be performed
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

val fatJar by tasks.creating(Jar::class.java) {
    dependsOn("build")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    group = "build"
    archiveFileName.set("app.jar")

    manifest {
        attributes["Implementation-Title"] = "Prizebot"
        attributes["Main-Class"] = "me.y9san9.prizebot.MainKt"
    }

    from (
        project.configurations
            .getByName("runtimeClasspath")
            .map { if(it.isDirectory) it else zipTree(it) }
    )

    with(project.tasks.getByName("jar") as CopySpec)
}
