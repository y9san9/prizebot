package me.y9san9.prizebot.bot.callback_query.command

import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import me.y9san9.prizebot.bot.shortcuts.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.logic.actor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.logic.actor.GiveawayMessageUpdater
import me.y9san9.prizebot.models.FinishedGiveaway
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.telegram.updates.message.Command


object ParticipationCommand {
    suspend fun handle(query: PrizebotCallbackQueryUpdate, command: Command) {
        val inlineMessageId = query.inlineMessageId ?: return
        val storage = query.di.participantsStorage
        val participantId = query.from.id.chatId
        val locale = Locale.with(query.languageCode)

        val giveaway = GiveawayFromCommandExtractor.extract(query.di, command)

        if(giveaway == null) {
            query.bot.editMessageText (
                inlineMessageId,
                entities = locale.thisGiveawayDeleted
            )
            return
        }

        val answer = when {
            giveaway is FinishedGiveaway -> locale.giveawayFinished
            giveaway.ownerId == participantId -> {
                locale.cannotParticipateInSelfGiveaway
            }
            storage.isParticipant(giveaway.id, participantId) -> {
                locale.alreadyParticipating
            }
            else -> {
                storage.saveParticipant(giveaway.id, participantId)
                locale.nowParticipating
            }
        }
        query.answer(answer)

        GiveawayMessageUpdater.update(query, giveaway)
    }
}
