package me.y9san9.prizebot.bot.di

import me.y9san9.prizebot.logic.database.GiveawaysStorage
import me.y9san9.prizebot.logic.database.ParticipantsStorage


data class PrizebotDI (
    val giveawaysStorage: GiveawaysStorage,
    val participantsStorage: ParticipantsStorage
)
