package me.y9san9.prizebot.conditions.tgbotapi

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.RawChatId
import dev.inmo.tgbotapi.types.chat.ExtendedPrivateChat
import dev.inmo.tgbotapi.types.chat.member.AdministratorChatMember
import dev.inmo.tgbotapi.types.chat.member.MemberChatMember
import dev.inmo.tgbotapi.types.toChatId
import me.y9san9.prizebot.conditions.BaseConditionsClient

class TgBotApiConditionsRepository(private val bot: TelegramBot) : BaseConditionsClient.ConditionsRepository {
    override suspend fun check(userId: Long, condition: BaseConditionsClient.Condition): Boolean =
        when (condition) {
            is BaseConditionsClient.Condition.MemberOfChannel ->
                checkJoin(userId, condition)
            is BaseConditionsClient.Condition.CanMention ->
                checkCanMention(userId)
            is BaseConditionsClient.Condition.PermanentUsername ->
                checkPermanentUsername(condition.channelId, condition.channelUsername)
        }

    private suspend fun checkCanMention(userId: Long): Boolean {
        println("> TgBotApiConditionsRepository: Check bot can mention user $userId")

        val participant = runCatching { (bot.getChat(userId.toChatId()) as? ExtendedPrivateChat) }
            .getOrNull() ?: run {
            println("> TgBotApiConditionsRepository: Cannot access user $userId")
            // We return true here, because we don't explicitly know whether user allow
            // forwards or not. But theoretically everyone on this step should have allowed
            // forwards.
            return true
        }

        return !participant.hasPrivateForwards.apply {
            println("> TgBotApiConditionsRepository: User $userId has private forwards: $this")
        }
    }

    private suspend fun checkJoin(userId: Long, condition: BaseConditionsClient.Condition.MemberOfChannel): Boolean {
        return runCatching {
            println("> TgBotApiConditionsRepository: Check user joined $userId to ${condition.channelId}")
            bot.getChatMember(condition.channelId.toChatId(), userId.toChatId())
                .takeIf { it is MemberChatMember || it is AdministratorChatMember }
        }.getOrNull().apply {
            println("> TgBotApiConditionsRepository: Check user joined $userId to ${condition.channelId}. Result: ${this != null}")
        } != null
    }

    private val checker = CheckPermanentUsernameRepository(bot = bot)

    private suspend fun checkPermanentUsername(channelId: Long, username: String): Boolean =
        checker.checkChannelUsername(channelId, username)
}
