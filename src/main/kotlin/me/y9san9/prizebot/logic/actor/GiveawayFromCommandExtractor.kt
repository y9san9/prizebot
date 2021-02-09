package me.y9san9.prizebot.logic.actor

import me.y9san9.prizebot.bot.di.PrizebotDI
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.telegram.updates.message.Command


object GiveawayFromCommandExtractor {
    fun extract(di: PrizebotDI, command: Command): Giveaway? {
        val giveawayId = command.args[0].toLongOrNull() ?: return null
        return di.giveawaysStorage.getById(giveawayId)
    }
}
