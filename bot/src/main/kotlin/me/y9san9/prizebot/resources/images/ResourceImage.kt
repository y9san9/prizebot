@file:Suppress("FunctionName")

package me.y9san9.prizebot.resources.images

import java.io.File
import kotlin.properties.ReadOnlyProperty


private class ResourceContainer

// Constructor
fun ResourceImage(name: String) = File (
    ResourceContainer::class.java
        .getResource("/images/$name")
        ?.toURI()
        ?: error("Resource image $name not found")
)

// Delegate
val resourceImage = ReadOnlyProperty<Any?, File> {
    _, property -> ResourceImage("${property.name}.png")
}
