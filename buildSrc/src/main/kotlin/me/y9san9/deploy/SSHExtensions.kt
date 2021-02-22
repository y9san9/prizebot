package me.y9san9.deploy


import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.delegateClosureOf
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.core.Service
import org.hidetake.groovy.ssh.session.SessionHandler


val Project.ssh get() = extensions.getByName("sshSession") as SSH

internal val Project.sshPlugin: Service get() = extensions.getByName("ssh") as Service

@Suppress("UNCHECKED_CAST")
val Project.remotes: NamedDomainObjectContainer<Remote> get() =
    extensions.getByName("remotes") as NamedDomainObjectContainer<Remote>

fun Service.session(webServer: Remote, handler: SessionHandler.() -> Unit): Any? =
    run(delegateClosureOf<RunHandler> {
        session(webServer, delegateClosureOf(handler))
    })

fun SessionHandler.put(vararg args: Pair<String, Any?>) = put(hashMapOf(*args))
