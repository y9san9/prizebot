package me.y9san9.prizebot.di

import me.y9san9.prizebot.database.giveaways_active_messages_storage.GiveawaysActiveMessagesStorage
import me.y9san9.prizebot.database.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.database.participants_storage.ParticipantsStorage


class PrizebotDI (
    giveawaysStorage: GiveawaysStorage,
    giveawaysActiveMessagesStorage: GiveawaysActiveMessagesStorage,
    languageCodesStorage: LanguageCodesStorage
) : GiveawaysStorage by giveawaysStorage,
    GiveawaysActiveMessagesStorage by giveawaysActiveMessagesStorage,
    LanguageCodesStorage by languageCodesStorage
