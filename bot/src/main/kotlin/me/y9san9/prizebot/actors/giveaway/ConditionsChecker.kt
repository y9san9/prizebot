package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.RequestException
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.ChatMember.MemberChatMember
import dev.inmo.tgbotapi.types.ChatMember.abstracts.AdministratorChatMember
import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.types.chat.abstracts.UsernameChat
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.extensions.list.on


sealed class CheckConditionsResult {
    object GiveawayInvalid : CheckConditionsResult()
    object NotSubscribedToConditions : CheckConditionsResult()
    class FriendsAreNotInvited(val invitedCount: Int, val requiredCount: Int) : CheckConditionsResult()
    object Success : CheckConditionsResult()
}

object ConditionsChecker {
    suspend fun cacheChatsUsernames(bot: TelegramBot, giveaway: ActiveGiveaway): Map<Long, String> =
        giveaway.conditions.list
            .filterIsInstance<Condition.Subscription>()
            .mapNotNull { condition ->
                val username = (bot.getChat(ChatId(condition.channelId)) as? UsernameChat)
                    ?.username?.username ?: return@mapNotNull null

                condition.channelId to username
            }.associate { it }

    suspend fun check (
        bot: TelegramBot, participantId: Long,
        giveaway: ActiveGiveaway
    ): CheckConditionsResult = check(bot, participantId, giveaway, cacheChatsUsernames(bot, giveaway))

    suspend fun check (
        bot: TelegramBot, participantId: Long,
        giveaway: ActiveGiveaway, cachedChatsUsernames: Map<Long, String>
    ): CheckConditionsResult {
        giveaway.conditions.list
            .on { condition: Condition.Subscription ->
                if(cachedChatsUsernames[condition.channelId] != condition.channelUsername)
                    return CheckConditionsResult.GiveawayInvalid

                try {
                    bot.getChatMember(ChatId(condition.channelId), UserId(participantId))
                        .takeIf { it is MemberChatMember || it is AdministratorChatMember }
                } catch (_: RequestException) { null }
                    ?: return CheckConditionsResult.NotSubscribedToConditions

            }.on { _: Condition.Invitations -> TODO() }

        return CheckConditionsResult.Success
    }
}
