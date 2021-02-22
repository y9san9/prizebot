package me.y9san9.random

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.*
import me.y9san9.random.models.Credentials
import me.y9san9.random.models.DrawRecordType
import me.y9san9.random.models.DrawResult
import me.y9san9.random.models.HoldDrawMethod
import java.util.concurrent.atomic.AtomicInteger


internal object RandomOrgAPI {
    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }

    private fun sequenceEndpointApi(min: Int, max: Int) =
        "https://www.random.org/sequences/?min=$min&max=$max&col=1&format=plain&rnd=new"

    suspend fun getSequence(min: Int, max: Int): List<Int> {
        val result = client.get<String>(sequenceEndpointApi(min, max))

        return result
            .lines()
            .filter(String::isNotEmpty)
            .map(String::toInt)
    }

    private const val drawsEndpointApi = "https://api.random.org/json-rpc/2/invoke"

    private var jsonrpcId = AtomicInteger(0)
    private val rpcResponses = MutableSharedFlow<JsonObject>()

    // RPC does not guarantee to return answers back in the order it was asked for them
    // So I've implemented checking by id, but if there will be more than one RPC request
    // The rpc logic should be encapsulated
    suspend fun holdDraw (
        credentials: Credentials,
        title: String,
        recordType: DrawRecordType,
        entries: List<String>,
        // in future will be used for multi-winners raffles
        winnerCount: Int = 1
    ) = coroutineScope {
        val rpcId = jsonrpcId.incrementAndGet()
        val deferred = async {
            rpcResponses.filter { it["id"]?.jsonPrimitive?.int == rpcId }.first()
        }
        rpcResponses.emit (
            client.post(drawsEndpointApi) {
                contentType(ContentType.Application.Json)

                body = HoldDrawMethod (
                    rpcId,
                    credentials, title, recordType,
                    entries, winnerCount
                )
            }
        )

        val json = deferred.await()["result"]?.jsonObject ?: error("Invalid api response")

        return@coroutineScope Json.decodeFromJsonElement<DrawResult>(json)
    }
}
