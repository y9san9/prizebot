package me.y9san9.prizebot.models.di

import me.y9san9.prizebot.actors.storage.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage


class PrizebotDI (
    giveawaysStorage: GiveawaysStorage,
    participantsStorage: ParticipantsStorage,
    giveawaysActiveMessagesStorage: GiveawaysActiveMessagesStorage,
) : GiveawaysStorage by giveawaysStorage,
    ParticipantsStorage by participantsStorage,
    GiveawaysActiveMessagesStorage by giveawaysActiveMessagesStorage
