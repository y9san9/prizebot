@file:Suppress("FunctionName")

package me.y9san9.prizebot.resources.images

import dev.inmo.tgbotapi.requests.abstracts.MultipartFile
import dev.inmo.tgbotapi.utils.BuiltinMimeTypes
import dev.inmo.tgbotapi.utils.StorageFile
import java.io.File
import kotlin.properties.ReadOnlyProperty


private class ResourceContainer

// Constructor
fun ResourceImage(name: String) = MultipartFile (
    StorageFile (
        name,
        ResourceContainer::class.java.getResourceAsStream("/images/$name").readBytes(),
        BuiltinMimeTypes.Image.Jpg
    )
)

// Delegate
val resourceImage = ReadOnlyProperty { _: Any?, property
    -> ResourceImage("${property.name}.png")
}
