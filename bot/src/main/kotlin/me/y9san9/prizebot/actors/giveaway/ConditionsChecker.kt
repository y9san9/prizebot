package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.RequestException
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.ChatMember.MemberChatMember
import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.types.chat.abstracts.UsernameChat
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.prizebot.extensions.list.on


sealed class CheckConditionsResult {
    object GiveawayInvalid : CheckConditionsResult()
    object NotSubscribedToConditions : CheckConditionsResult()
    class FriendsAreNotInvited(val invitedCount: Int, val requiredCount: Int) : CheckConditionsResult()
    object Success : CheckConditionsResult()
}

object ConditionsChecker {
    suspend fun check(bot: TelegramBot, participantId: Long, giveaway: ActiveGiveaway): CheckConditionsResult {
        giveaway.conditions.list
            .on { condition: Condition.Subscription ->
                val channel = try {
                    bot.getChat(ChatId(condition.channelId)) as? UsernameChat
                } catch (_: RequestException) { null }
                    ?: return CheckConditionsResult.GiveawayInvalid

                if(channel.username?.username != condition.channelUsername)
                    return CheckConditionsResult.GiveawayInvalid

                try {
                    bot.getChatMember(channel.id, UserId(participantId))
                        .takeIf { it is MemberChatMember }
                } catch (_: RequestException) { null }
                    ?: return CheckConditionsResult.NotSubscribedToConditions

            }.on { _: Condition.Invitations -> TODO() }

        return CheckConditionsResult.Success
    }
}
