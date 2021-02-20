@file:Suppress("LocalVariableName")

package me.y9san9.telegram.updates.extensions.text

import me.y9san9.telegram.updates.primitives.HasTextUpdate


/**
 * @return if [handler] was called
 */
inline fun HasTextUpdate.text(handler: (String) -> Unit): Boolean {
    val _text = text

    if(_text != null)
        handler(_text)

    return _text != null
}
