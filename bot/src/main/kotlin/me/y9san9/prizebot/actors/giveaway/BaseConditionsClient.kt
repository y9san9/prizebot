package me.y9san9.prizebot.actors.giveaway

import me.y9san9.prizebot.conditions.BaseConditionsClient
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition

suspend fun BaseConditionsClient.check(
    userId: Long,
    giveaway: ActiveGiveaway,
    discriminator: Any = userId,
    firstHandler: suspend (BaseConditionsClient.Result) -> Unit = {}
) = check(
    userId = userId,
    conditions = giveaway.conditions.list.flatMap { condition ->
        when (condition) {
            is Condition.Invitations -> TODO()
            is Condition.Subscription -> listOf(
                BaseConditionsClient.Condition.PermanentUsername(condition.channelId, condition.channelUsername),
                BaseConditionsClient.Condition.MemberOfChannel(condition.channelId)
            )
        }
    } + BaseConditionsClient.Condition.CanMention,
    discriminator = discriminator,
    firstHandler = firstHandler
)
