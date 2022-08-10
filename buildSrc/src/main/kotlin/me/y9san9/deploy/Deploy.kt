package me.y9san9.deploy

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import java.io.File

open class DeployConfiguration {
    lateinit var mainClassName: String
    lateinit var implementationTitle: String
    lateinit var host: String
    lateinit var user: String
    lateinit var password: String
    lateinit var serviceName: String
    lateinit var deployPath: String

    var knownHostsFile: String? = null

    internal val mainClassNameInitialized get() = ::mainClassName.isInitialized
    internal val implementationTitleInitialized get() = ::implementationTitle.isInitialized
    internal val hostInitialized get() = ::host.isInitialized
    internal val userInitialized get() = ::user.isInitialized
    internal val passwordInitialized get() = ::password.isInitialized
    internal val serviceNameInitialized get() = ::serviceName.isInitialized
    internal val deployPathInitialized get() = ::deployPath.isInitialized
}

class Deploy : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply(plugin = "application")
        target.apply(plugin = "org.hidetake.ssh")

        val configuration = target.extensions.create<DeployConfiguration>(name = "deploy")

        target.afterEvaluate {
            if(!configuration.mainClassNameInitialized)
                error("You should specify main class name to deploy")
            if(!configuration.implementationTitleInitialized)
                error("You should specify implementation title")
            if(!configuration.hostInitialized)
                error("You should specify host where to deploy")
            if(!configuration.userInitialized)
                error("You should specify user to login ssh")
            if(!configuration.passwordInitialized)
                error("You should specify password to login ssh")
            if(!configuration.serviceNameInitialized)
                error("You should specify service name to restart")
            if(!configuration.deployPathInitialized)
                error("You should specify ssh deploy path")

            val webServer = Remote (
                mapOf (
                    "host" to configuration.host,
                    "user" to configuration.user,
                    "password" to configuration.password,
                    "knownHosts" to (configuration.knownHostsFile?.let(::File) ?: AllowAnyHosts.instance)
                )
            )

            target.extensions.create<SSH>("sshSession", target, webServer)

            val fatJar = target.task("fatJar", type = Jar::class) {
                dependsOn("build")

                group = "build"
                archiveFileName.set("app.jar")

                manifest {
                    attributes["Implementation-Title"] = configuration.implementationTitle
                }

                from (
                    project.configurations
                        .getByName("runtimeClasspath")
                        .map { if(it.isDirectory) it else target.zipTree(it) }
                )

                with(project.tasks.getByName("jar") as CopySpec)
            }

            target.tasks.withType<Jar> {
                duplicatesStrategy = DuplicatesStrategy.INCLUDE

                manifest {
                    attributes(mapOf("Main-Class" to configuration.mainClassName))
                }
            }

            target.task("deploy") {
                group = "deploy"

                dependsOn(fatJar)

                doLast {
                    sshPlugin.session(webServer) {
                        put("from" to fatJar.archiveFile.get().asFile, "into" to configuration.deployPath)
                        execute("systemctl restart ${configuration.serviceName}")
                    }
                }
            }
        }
    }
}
