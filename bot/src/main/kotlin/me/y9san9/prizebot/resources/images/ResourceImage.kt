@file:Suppress("FunctionName")

package me.y9san9.prizebot.resources.images

import java.io.File
import kotlin.properties.ReadOnlyProperty


private val home = File(System.getProperty("user.dir")) / "bot" / "src" / "main" / "resources" / "images"

private operator fun File.div(path: String) = File(this, path)

// Constructor
fun ResourceImage(name: String) = home / name

// Delegate
val resourceImage = ReadOnlyProperty<Any?, File> {
    _, property -> ResourceImage(property.name + ".png")
}
