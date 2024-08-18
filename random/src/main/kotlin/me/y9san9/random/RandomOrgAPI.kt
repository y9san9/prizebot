package me.y9san9.random

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.setBody
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.*
import me.y9san9.random.rpc.integers.GenerateIntegersMethod
import me.y9san9.rpc.JsonRPC


internal object RandomOrgAPI {
    private const val endpointApi = "https://api.random.org/json-rpc/2/invoke"

    private val client = HttpClient(CIO) {
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json()
        }

        install(UserAgent) {
            // Email is required, but probably sources link will be better
            agent = "Open Source Telegram Prizebot (https://github.com/y9san9/prizebot)"
        }
    }

    private val rpc = JsonRPC(client)

    suspend fun getRandomIntegers(apiKey: String, min: Int, max: Int, count: Int = 100): Result<List<Int>> =
        coroutineScope {
            runCatching {
                val json = rpc.request(scope = this, endpointApi) { rpcId ->
                    setBody(GenerateIntegersMethod(rpcId, apiKey, count, min, max))
                }["result"]?.jsonObject?.get("random")
                    ?.jsonObject?.get("data") ?: error("Invalid API response")

                Json.decodeFromJsonElement<List<Int>>(json)
                    .takeIf { it.size == count } ?: error("Invalid API response")
            }
        }

}
