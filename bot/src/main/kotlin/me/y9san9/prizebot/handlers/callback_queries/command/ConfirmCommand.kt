package me.y9san9.prizebot.handlers.callback_queries.command

import me.y9san9.prizebot.actors.telegram.updater.ConfirmationMessage
import me.y9san9.prizebot.extensions.telegram.PrizebotCallbackQueryUpdate
import me.y9san9.prizebot.extensions.telegram.getLocale
import me.y9san9.prizebot.resources.CALLBACK_ACTION_DELETE_GIVEAWAY
import me.y9san9.prizebot.resources.CALLBACK_ACTION_RAFFLE_GIVEAWAY
import me.y9san9.telegram.updates.extensions.command.command
import me.y9san9.telegram.updates.extensions.command.parseCommand


object ConfirmCommand {
    suspend fun handle (
        update: PrizebotCallbackQueryUpdate,
        splitter: String = "_",
        actionSplitter: String = "+"
    ) {
        val command = update.command(splitter) ?: return

        val confirmationAction = command.args[0].replace(actionSplitter, splitter)
        val cancelAction = command.args[1].replace(actionSplitter, splitter)

        val confirmationText = when(confirmationAction.parseCommand(splitter).text) {
            "$CALLBACK_ACTION_DELETE_GIVEAWAY" -> update.getLocale().deleteGiveawayConfirmation
            "$CALLBACK_ACTION_RAFFLE_GIVEAWAY" -> update.getLocale().raffleGiveawayConfirmation
            else -> error("Unknown confirmation")
        }

        ConfirmationMessage.confirm(update, confirmationText, confirmationAction, cancelAction)
        update.answer()
    }
}
