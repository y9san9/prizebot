package me.y9san9.prizebot.bot.callback_query.command

import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.utils.asUsernameChat
import dev.inmo.tgbotapi.types.ChatId
import me.y9san9.prizebot.bot.shortcuts.*
import me.y9san9.prizebot.logic.actor.GiveawayFromCommandExtractor
import me.y9san9.prizebot.logic.actor.GiveawayMessageUpdater
import me.y9san9.prizebot.logic.actor.RaffleActor
import me.y9san9.prizebot.models.ActiveGiveaway
import me.y9san9.telegram.updates.message.Command


object RaffleCommand {

    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun handle(query: PrizebotCallbackQueryUpdate, command: Command) {
        val storage = query.di.participantsStorage

        var giveaway = GiveawayFromCommandExtractor.extract(query.di, command) ?: return

        if(giveaway is ActiveGiveaway) {
            suspend fun getUsername(id: Long) = query.bot.getChat(ChatId(id)).asUsernameChat()?.username

            val winnerId = RaffleActor
                .raffle(giveaway.id, storage) { getUsername(it) != null }

            if (winnerId == null) {
                query.answer(text = query.locale.nobodyIsParticipatingYet)
                return
            } else {
                query.di.giveawaysStorage.finishGiveaway(giveaway.id, winnerId)
                giveaway = query.di.giveawaysStorage.getById(giveaway.id) ?: error("LUL WHAT??")
            }
        }

        GiveawayMessageUpdater.update(query, giveaway, demo = true)
        query.answer()
    }
}
