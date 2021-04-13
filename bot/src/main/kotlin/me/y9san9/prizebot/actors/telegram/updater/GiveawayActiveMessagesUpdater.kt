package me.y9san9.prizebot.actors.telegram.updater

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.resources.content.giveawayContent
import me.y9san9.telegram.updates.hierarchies.DIBotUpdate


object GiveawayActiveMessagesUpdater {
    private val scope = CoroutineScope(context = SupervisorJob())

    fun <TDI> update (
        update: DIBotUpdate<TDI>, giveawayId: Long
    ) where TDI : GiveawaysActiveMessagesStorage,
            TDI : GiveawaysStorage = scope.launch {

        val giveaway = update.di.getGiveawayById(giveawayId) ?: return@launch

        for(activeMessage in update.di.getActiveMessage(giveawayId)) launch {
            safelyWithoutExceptions {
                val (entities, markup) = giveawayContent(update, giveaway)
                update.bot.editMessageText(activeMessage, entities, replyMarkup = markup)
            }
        }
    }
}
