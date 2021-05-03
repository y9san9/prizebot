@file:Suppress("FunctionName")

package me.y9san9.random.rpc.integers

import kotlinx.serialization.json.put
import me.y9san9.rpc.jsonRPCMethod


fun GenerateIntegersMethod (
    rpcId: Int,
    apiKey: String,
    n: Int,
    min: Int,
    max: Int
) = jsonRPCMethod(method = "generateIntegers", rpcId) {
    put("apiKey", apiKey)
    put("n", n)
    put("min", min)
    put("max", max)
}
