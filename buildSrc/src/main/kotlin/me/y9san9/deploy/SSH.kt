package me.y9san9.deploy

import org.gradle.api.Project
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.session.SessionHandler


open class SSH (private val project: Project, private val webServer: Remote) {
    operator fun invoke(handler: SessionHandler.() -> Unit) {
        project.sshPlugin.session(webServer, handler)
    }
}
