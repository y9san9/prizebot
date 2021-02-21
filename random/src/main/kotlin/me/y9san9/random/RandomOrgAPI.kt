package me.y9san9.random

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*


internal object RandomOrgAPI {
    // private const val drawsEndpointApi = "https://api.random.org/json-rpc/2/invoke"
    private val client = HttpClient(CIO)

    private fun sequenceEndpointApi(min: Int, max: Int) =
        "https://www.random.org/sequences/?min=$min&max=$max&col=1&format=plain&rnd=new"

    suspend fun getSequence(min: Int, max: Int): List<Int> {
        val result = client.get<String>(sequenceEndpointApi(min, max))

        return result
            .lines()
            .filter(String::isNotEmpty)
            .map(String::toInt)
    }
}
