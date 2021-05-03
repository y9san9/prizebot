package me.y9san9.random

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.*
import me.y9san9.random.models.Credentials
import me.y9san9.random.rpc.draws.DrawRecordType
import me.y9san9.random.rpc.draws.DrawResult
import me.y9san9.random.rpc.draws.HoldDrawMethod
import me.y9san9.random.rpc.integers.GenerateIntegersMethod
import me.y9san9.rpc.JsonRPC


internal object RandomOrgAPI {
    private const val endpointApi = "https://api.random.org/json-rpc/2/invoke"

    private val client = HttpClient(CIO) {
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }
        install(JsonFeature)

        install(UserAgent) {
            // Email is required, but probably sources link will be better
            agent = "Open Source Telegram Prizebot (https://github.com/y9san9/prizebot)"
        }
    }

    private val rpc = JsonRPC(client)

    suspend fun getRandomIntegers(apiKey: String, min: Int, max: Int, count: Int = 100): List<Int> =
        coroutineScope {
            val json = rpc.request(scope = this, endpointApi) { rpcId ->
                body = GenerateIntegersMethod(rpcId, apiKey, count, min, max)
            }["result"]?.jsonObject?.get("random")
                ?.jsonObject?.get("data") ?: error("Invalid API response")

            return@coroutineScope Json.decodeFromJsonElement<List<Int>>(json)
                .takeIf { it.size == count } ?: error("Invalid API response")
        }

    suspend fun holdDraw (
        credentials: Credentials,
        title: String,
        recordType: DrawRecordType,
        entries: List<String>,
        winnerCount: Int = 1
    ) = coroutineScope {
        val json = rpc.request(scope = this, endpointApi) { rpcId ->
            body = HoldDrawMethod (
                rpcId,
                credentials, title, recordType,
                entries, winnerCount
            )
        }["result"]?.jsonObject ?: error("Invalid API response")

        return@coroutineScope Json.decodeFromJsonElement<DrawResult>(json)
    }
}
