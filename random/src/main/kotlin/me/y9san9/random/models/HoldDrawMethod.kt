@file:Suppress("SpellCheckingInspection", "FunctionName")

package me.y9san9.random.models

import kotlinx.serialization.json.*
import me.y9san9.random.utils.md5
import me.y9san9.random.utils.toHex


fun HoldDrawMethod (
    id: Int,
    credentials: Credentials,
    title: String,
    recordType: DrawRecordType,
    entries: List<String>,
    winnerCount: Int
): JsonObject = buildJsonObject {
    put("jsonrpc", "2.0")
    put("method", "holdDraw")

    put("params", buildJsonObject {
        val entriesEncoded = Json.encodeToJsonElement(entries)

        put("title", title)
        put("entriesDigest", md5(entriesEncoded.toString()).toHex())
        put("winnerCount", winnerCount)

        put("credentials", Json.encodeToJsonElement(credentials))
        put("recordType", Json.encodeToJsonElement(recordType))
        put("entries", entriesEncoded)
    })

    put("id", id)
}
