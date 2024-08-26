@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.prizebot.handlers.callback_queries.command

import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.toChatId
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.y9san9.prizebot.actors.giveaway.check
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayActiveMessagesUpdater
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.conditions.BaseConditionsClient
import me.y9san9.prizebot.database.giveaways_storage.ActiveGiveaway
import me.y9san9.prizebot.database.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.extensions.telegram_bot.getUserTitleOrNull

object ParticipateCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate): Unit = coroutineScope {
        val participantId = update.userId
        val locale = update.getLocale()

        launch {
            update.bot.getUserTitleOrNull(update.userId)?.let {
                update.di.saveUserTitle(update.userId, it)
            }
        }

        println("BEFORE EXTRACTION! ${update.query}")

        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_")
            ?: return@coroutineScope update.answer(locale.thisGiveawayDeleted)

        println("EXTRACTED! $giveaway ${update.query}")

        val answer = when {
            giveaway is FinishedGiveaway -> ProcessResult(locale.giveawayFinished)
            giveaway.ownerId == participantId -> ProcessResult(locale.cannotParticipateInSelfGiveaway)
            giveaway.isParticipant(participantId) -> {
                // this line is commented to prevent spam. it is not too useful to have an ability to leave giveaway.
//                giveaway.removeParticipant(participantId)
//                locale.youHaveLeftGiveaway
                ProcessResult(locale.alreadyParticipating)
            }
            else -> when(
                val result = update.di.conditionsClient.check(
                    userId = participantId,
                    giveaway = giveaway as ActiveGiveaway,
                    firstHandler = { result ->
                        val failedCondition = result.await().condition
                        if (failedCondition == null) {
                            giveaway.saveParticipant(participantId)
                        }
                        if (result is BaseConditionsClient.Result.HighLoad) {
                            sendStatusMessage(update, failedCondition)
                        }
                    }
                )
            ) {
                is BaseConditionsClient.Result.Calculated ->
                    processCondition(result.condition, update.getLocale())
                is BaseConditionsClient.Result.HighLoad ->
                    ProcessResult(locale.highLoadMessage, showAlert = true)
            }
        }

        println("CHECKED CONDITIONS! ${update.query}")

        update.answer(answer.message, showAlert = answer.showAlert)


        println("ANSWERED! ${update.query}")

        GiveawayActiveMessagesUpdater.update(update, giveaway.id)

        println("GOING UPDATE! ${update.query}")
    }

    private fun sendStatusMessage(
        update: PrizebotCallbackQueryUpdate,
        condition: BaseConditionsClient.Condition?
    ) {
        update.di.scope.launch {
            runCatching {
                update.bot.sendMessage(
                    chatId = update.userId.toChatId(),
                    text = processCondition(condition, update.getLocale()).message
                )
            }
        }
    }

    private fun processCondition(
        condition: BaseConditionsClient.Condition?,
        locale: Locale
    ): ProcessResult = when (condition) {
        is BaseConditionsClient.Condition.CanMention ->
            ProcessResult(locale.cannotMentionsUser, showAlert = true)
        is BaseConditionsClient.Condition.MemberOfChannel ->
            ProcessResult(locale.notSubscribedToConditions)
        is BaseConditionsClient.Condition.PermanentUsername ->
            ProcessResult(locale.giveawayInvalid)
        null -> ProcessResult(locale.nowParticipating)
    }

    private class ProcessResult(
        val message: String,
        val showAlert: Boolean = false
    )
}
