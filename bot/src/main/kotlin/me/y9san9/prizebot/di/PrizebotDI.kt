package me.y9san9.prizebot.di

import kotlinx.coroutines.CoroutineScope
import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.actors.giveaway.RaffleActor
import me.y9san9.prizebot.conditions.BaseConditionsClient
import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.database.linked_channels_storage.LinkedChannelsStorage
import me.y9san9.prizebot.database.user_titles_storage.UserTitlesStorage

class PrizebotDI (
    giveawaysStorage: GiveawaysStorage,
    giveawaysActiveMessagesStorage: GiveawaysActiveMessagesStorage,
    languageCodesStorage: LanguageCodesStorage,
    linkedChannelsStorage: LinkedChannelsStorage,
    userTitlesStorage: UserTitlesStorage,
    val raffleActor: RaffleActor,
    val autoRaffleActor: AutoRaffleActor,
    val conditionsClient: BaseConditionsClient,
    val scope: CoroutineScope
) : GiveawaysStorage by giveawaysStorage,
    GiveawaysActiveMessagesStorage by giveawaysActiveMessagesStorage,
    LanguageCodesStorage by languageCodesStorage,
    LinkedChannelsStorage by linkedChannelsStorage,
    UserTitlesStorage by userTitlesStorage
