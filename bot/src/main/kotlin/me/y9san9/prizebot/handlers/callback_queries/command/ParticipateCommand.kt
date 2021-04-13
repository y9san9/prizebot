package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.database.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.database.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.actors.telegram.extractor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.actors.telegram.updater.GiveawayCallbackQueryMessageUpdater
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate


object ParticipateCommand {
    suspend fun handle(update: PrizebotCallbackQueryUpdate) {
        val participantId = update.chatId
        val locale = update.locale

        val giveaway = GiveawayFromCommandExtractor.extract(update, splitter = "_")
            ?: return update.answer(locale.thisGiveawayDeleted)

        val answer = when {
            giveaway is FinishedGiveaway -> locale.giveawayFinished
            giveaway.ownerId == participantId -> {
                locale.cannotParticipateInSelfGiveaway
            }
            giveaway.isParticipant(participantId) -> {
                locale.alreadyParticipating
            }
            else -> {
                giveaway.saveParticipant(participantId)
                locale.nowParticipating
            }
        }
        update.answer(answer)

        GiveawayCallbackQueryMessageUpdater.update(update, giveaway)
    }
}
