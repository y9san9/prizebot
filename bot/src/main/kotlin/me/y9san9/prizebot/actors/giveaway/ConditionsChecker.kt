package me.y9san9.prizebot.actors.giveaway

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.exceptions.RequestException
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.chat.members.getChatMember
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.ExtendedPrivateChat
import dev.inmo.tgbotapi.types.chat.UsernameChat
import dev.inmo.tgbotapi.types.chat.member.AdministratorChatMember
import dev.inmo.tgbotapi.types.chat.member.MemberChatMember
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import me.y9san9.extensions.list.on
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import kotlin.coroutines.suspendCoroutine


sealed class CheckConditionsResult {
    object GiveawayInvalid : CheckConditionsResult()
    object NotSubscribedToConditions : CheckConditionsResult()
    object CannotMentionUser : CheckConditionsResult()
    class FriendsAreNotInvited(val invitedCount: Int, val requiredCount: Int) : CheckConditionsResult()
    object Success : CheckConditionsResult()
}

object ConditionsChecker {
    private val checkingQueue = Channel<CheckConditions>()
    private val scope = CoroutineScope(Dispatchers.IO) + CoroutineName("Conditions Checker")

    init {
        scope.launch {
            val processing = mutableListOf<Processing>()

            for (checkRequest in checkingQueue) {
                val alreadyProcessing = processing.firstOrNull {
                    it.participantId == checkRequest.participantId && it.giveawayId == checkRequest.giveaway.id
                }
                if (alreadyProcessing != null) {
                    return@launch checkRequest.onCheck(alreadyProcessing.deferred.await())
                }

                val newProcessing = Processing(
                    participantId = checkRequest.participantId,
                    giveawayId = checkRequest.giveaway.id,
                    deferred = async {
                        checkDeferredJob(
                            bot = checkRequest.bot,
                            participantId = checkRequest.participantId,
                            giveaway = checkRequest.giveaway,
                            cachedChatsUsernames = checkRequest.cachedChatsUsernames
                        ).also { result ->
                            checkRequest.onCheck(result)
                            processing.removeAll {
                                it.participantId == checkRequest.participantId &&
                                        it.giveawayId == checkRequest.giveaway.id
                            }
                        }
                    }
                )

                processing += newProcessing
            }
        }
    }

    suspend fun cacheChatsUsernames(bot: TelegramBot, giveaway: ActiveGiveaway): Map<Long, String> =
        giveaway.conditions.list
            .filterIsInstance<Condition.Subscription>()
            .mapNotNull { condition ->
                val username = try {
                        (bot.getChat(ChatId(condition.channelId)) as? UsernameChat)?.username?.username
                } catch (_: Throwable) { null }
                    ?: return@mapNotNull null

                condition.channelId to username
            }.associate { it }

    suspend fun check (
        bot: TelegramBot, participantId: Long,
        giveaway: ActiveGiveaway
    ): CheckConditionsResult = check(bot, participantId, giveaway, cacheChatsUsernames(bot, giveaway))

    suspend fun check(
        bot: TelegramBot, participantId: Long,
        giveaway: ActiveGiveaway, cachedChatsUsernames: Map<Long, String>
    ): CheckConditionsResult = suspendCoroutine { continuation ->
        scope.launch {
            checkingQueue.send(
                CheckConditions(
                    bot = bot,
                    participantId = participantId,
                    giveaway = giveaway,
                    cachedChatsUsernames = cachedChatsUsernames
                ) { result -> continuation.resumeWith(Result.success(result)) }
            )
        }
    }

    private suspend fun checkDeferredJob(
        bot: TelegramBot,
        participantId: Long,
        giveaway: ActiveGiveaway,
        cachedChatsUsernames: Map<Long, String>
    ): CheckConditionsResult {
        val participant = (bot.getChat(ChatId(participantId)) as? ExtendedPrivateChat)
            ?: return CheckConditionsResult.CannotMentionUser

        if (participant.hasPrivateForwards)
            return CheckConditionsResult.CannotMentionUser

        giveaway.conditions.list
            .on { condition: Condition.Subscription ->
                if(cachedChatsUsernames[condition.channelId] != condition.channelUsername)
                    return CheckConditionsResult.GiveawayInvalid

                try {
                    bot.getChatMember(ChatId(condition.channelId), ChatId(participantId))
                        .takeIf { it is MemberChatMember || it is AdministratorChatMember }
                } catch (_: RequestException) { null }
                    ?: return CheckConditionsResult.NotSubscribedToConditions

            }.on { _: Condition.Invitations -> TODO() }

        return CheckConditionsResult.Success
    }

    private class CheckConditions(
        val bot: TelegramBot,
        val participantId: Long,
        val giveaway: ActiveGiveaway,
        val cachedChatsUsernames: Map<Long, String>,
        val onCheck: (CheckConditionsResult) -> Unit
    )

    private class Processing(
        val participantId: Long,
        val giveawayId: Long,
        val deferred: Deferred<CheckConditionsResult>
    )
}
