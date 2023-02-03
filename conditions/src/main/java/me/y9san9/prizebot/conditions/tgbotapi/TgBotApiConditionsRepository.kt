package me.y9san9.prizebot.conditions.tgbotapi

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.ExtendedPrivateChat
import dev.inmo.tgbotapi.types.chat.member.AdministratorChatMember
import dev.inmo.tgbotapi.types.chat.member.MemberChatMember
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
        val participant = runCatching { (bot.getChat(ChatId(userId)) as? ExtendedPrivateChat) }.getOrNull()
            ?: return false

        return !participant.hasPrivateForwards
    }

    private suspend fun checkJoin(userId: Long, condition: BaseConditionsClient.Condition.MemberOfChannel): Boolean {
        return runCatching {
            bot.getChatMember(ChatId(condition.channelId), ChatId(userId))
                .takeIf { it is MemberChatMember || it is AdministratorChatMember }
        }.getOrNull() != null
    }

    private val checker = CheckPermanentUsernameRepository(bot = bot)

    private suspend fun checkPermanentUsername(channelId: Long, username: String): Boolean =
        checker.checkChannelUsername(channelId, username)
}
