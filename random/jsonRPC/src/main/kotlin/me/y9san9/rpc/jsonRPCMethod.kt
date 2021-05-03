package me.y9san9.rpc

import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


fun jsonRPCMethod (
    method: String,
    rpcId: Int,
    jsonRPC: String = "2.0",
    builder: JsonObjectBuilder.() -> Unit
) = buildJsonObject {
    put("id", rpcId)
    put("jsonrpc", jsonRPC)
    put("method", method)

    put("params", buildJsonObject(builder))
}
