package me.y9san9.prizebot.database.giveaways_storage.conditions_storage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


sealed interface CheckedPositiveInt

object PositiveIntRequired : CheckedPositiveInt

@Serializable
inline class PositiveInt private constructor(val int: Int) : CheckedPositiveInt {
    companion object {
        fun create(int: Int): PositiveInt {
            val createTry = createChecked(int)
            require(createTry is PositiveInt)
            return createTry
        }
        fun createChecked(int: Int) = when {
            int <= 0 -> PositiveIntRequired
            else -> PositiveInt(int)
        }
    }
}


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


sealed interface CheckedGiveawayConditions

object OnlyOneInvitationConditionAllowed : CheckedGiveawayConditions
object ChannelConditionRequiredForInvitations : CheckedGiveawayConditions

@Serializable
// It is not inline because of serialization bug :(
/*inline*/ class GiveawayConditions private constructor (
    val list: List<Condition>
) : CheckedGiveawayConditions {

    companion object {
        fun create(list: List<Condition>): GiveawayConditions {
            val createTry = createChecked(list)
            require(createTry is GiveawayConditions)
            return createTry
        }
        fun createChecked(list: List<Condition>) = when {
            list.count { it is Condition.Invitations } > 1 -> OnlyOneInvitationConditionAllowed
            list.filterIsInstance<Condition.Invitations>().isNotEmpty() &&
                    list.filterIsInstance<Condition.Subscription>().isEmpty() ->
                ChannelConditionRequiredForInvitations
            else -> GiveawayConditions(list)
        }
    }
}
