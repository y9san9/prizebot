@file:Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")

package me.y9san9.prizebot.database.giveaways_storage.conditions_storage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
inline class PositiveInt internal constructor(val int: Int) {
    init {
        require(int > 0) { "The value must be positive" }
    }
}

fun Int.wrapPositiveInt() = PositiveInt(this)


@Serializable
sealed class Condition {
    @Serializable
    @SerialName("subscription")
    data class Subscription(
        val channelId: Long,
        val channelUsername: String
    ) : Condition()

    @Serializable
    @SerialName("invitations")
    data class Invitations (
        val count: PositiveInt
    ) : Condition()
}


@Serializable
// It is not inline because of serialization bug :(
/*inline*/ class GiveawayConditions internal constructor (
    val list: List<Condition>
) {
    init {
        require(list.count { it is Condition.Invitations } <= 1) {
            "Only one invitations condition allowed"
        }
        require(list.filterIsInstance<Condition.Invitations>().isEmpty()
                || list.filterIsInstance<Condition.Subscription>().isNotEmpty()) {
            "You should add at least one channel in case you want to invite friends"
        }
    }
}

fun List<Condition>.wrapGiveawayConditions() = GiveawayConditions(list = this)
