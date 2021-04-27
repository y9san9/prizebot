package me.y9san9.prizebot.handlers.choosen_inline_result

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.y9san9.prizebot.extensions.telegram.PrizebotChosenInlineResultUpdate
import me.y9san9.prizebot.handlers.choosen_inline_result.commands.PickGiveawayCommand
import me.y9san9.prizebot.handlers.choosen_inline_result.commands.SendGiveawayCommand
import me.y9san9.prizebot.resources.INLINE_ACTION_SEND_GIVEAWAY
import me.y9san9.telegram.updates.extensions.command.command


class ChosenInlineResultHandler (
    private val scope: CoroutineScope
) {
    fun launchHandle(update: PrizebotChosenInlineResultUpdate) = scope.launch {
        handle(update)
    }

    private fun handle(update: PrizebotChosenInlineResultUpdate) = update.command(splitter = "_") {
        raw("") {
            PickGiveawayCommand.handle(update)
        }
        case("$INLINE_ACTION_SEND_GIVEAWAY", argsCount = 1) {
            SendGiveawayCommand.handle(update)
        }
    }
}
