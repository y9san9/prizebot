package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.storage.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate


object ParticipateCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val storage = update.di as ParticipantsStorage
        val participantId = update.chatId
        val locale = update.locale

        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_")

        val answer = when {
            giveaway == null -> null
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
        update.answer(answer)

        GiveawayCallbackQueryMessageUpdater.update(update, giveaway)
    }
}
