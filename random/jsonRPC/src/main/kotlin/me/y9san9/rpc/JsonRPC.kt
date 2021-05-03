package me.y9san9.rpc

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive
import java.util.concurrent.atomic.AtomicInteger


class JsonRPC (
    private val client: HttpClient,
    private val rpcIdProvider: suspend (JsonObject) -> Int? = { it["id"]?.jsonPrimitive?.intOrNull }
) {
    private val responses = MutableSharedFlow<JsonObject>()
    private val rpcId = AtomicInteger(0)

    suspend fun request (
        scope: CoroutineScope,
        endpointApi: String,
        requestBody: HttpRequestBuilder.(rpcId: Int) -> Unit
    ): JsonObject {
        val rpcId = rpcId.incrementAndGet()

        scope.launch {
            responses.emit (
                client.post(endpointApi) { requestBody(rpcId) }
            )
        }

        return responses.first { rpcIdProvider(it) == rpcId }
    }
}
