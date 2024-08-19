package me.y9san9.rpc

import io.ktor.client.*
import io.ktor.client.call.body
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


class JsonRPC(
    private val client: HttpClient
) {
    private val rpcId = AtomicInteger(0)

    suspend fun request(
        endpointApi: String,
        requestBody: HttpRequestBuilder.(rpcId: Int) -> Unit
    ): Result<JsonObject> {
        val rpcId = rpcId.incrementAndGet()

        val result: Result<JsonObject> = runCatching {
            client.post(endpointApi) { requestBody(rpcId) }.body()
        }

        return result
    }
}
