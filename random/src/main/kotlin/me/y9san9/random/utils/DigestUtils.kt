package me.y9san9.random.utils

import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest


fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
