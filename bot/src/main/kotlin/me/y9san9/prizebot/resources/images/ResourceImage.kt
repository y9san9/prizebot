@file:Suppress("FunctionName")

package me.y9san9.prizebot.resources.images

import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.utils.BuiltinMimeTypes
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlin.properties.ReadOnlyProperty


private class ResourceContainer

// Constructor
fun ResourceImage(name: String) = MultipartFile(
    filename = name,
) {
    ResourceContainer::class.java
        .getResourceAsStream("/images/$name")
        ?.asInput()
        ?: error("Image $name not found")
}

// Delegate
val resourceImage = ReadOnlyProperty { _: Any?, property
    -> ResourceImage("${property.name}.png")
}
