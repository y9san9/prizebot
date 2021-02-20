package me.y9san9.prizebot.models.di

import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage


class PrizebotDI (
    giveawaysStorage: GiveawaysStorage,
    participantsStorage: ParticipantsStorage,
) : GiveawaysStorage by giveawaysStorage,
    ParticipantsStorage by participantsStorage
