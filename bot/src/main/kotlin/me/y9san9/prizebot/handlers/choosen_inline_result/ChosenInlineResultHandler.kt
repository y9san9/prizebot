package me.y9san9.prizebot.handlers.choosen_inline_result

import me.y9san9.prizebot.extensions.telegram.PrizebotChosenInlineResultUpdate
import me.y9san9.prizebot.handlers.choosen_inline_result.commands.PickGiveawayCommand
import me.y9san9.prizebot.handlers.choosen_inline_result.commands.SendGiveawayCommand
import me.y9san9.prizebot.resources.INLINE_ACTION_SEND_GIVEAWAY
import me.y9san9.telegram.updates.extensions.command.command


object ChosenInlineResultHandler {
    suspend fun handle(update: PrizebotChosenInlineResultUpdate) = update.command(splitter = "_") {
        raw("") {
            PickGiveawayCommand.handle(update)
        }
        case("$INLINE_ACTION_SEND_GIVEAWAY", argsCount = 1) {
            SendGiveawayCommand.handle(update)
        }
    }
}
