package me.y9san9.random.models

import kotlinx.serialization.Serializable


@Serializable
data class DrawResult (
    val drawId: Int?,
    val status: String,
    val entryCount: Int,
    val winners: List<String>,
    val completionTime: String,
    val recordUrl: String?
)
