@file:Suppress("unused")

package me.y9san9.random.rpc.draws

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class DrawRecordType {
    @SerialName("public")
    Public,
    @SerialName("private")
    Private,
    @SerialName("entrantAccessible")
    EntrantAccessible,
    @SerialName("test")
    Test
}