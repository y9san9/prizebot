package me.y9san9.telegram.updates.hierarchies

import me.y9san9.telegram.updates.primitives.LocalizedUpdate


interface FromChatLocalizedBotUpdate : FromChatBotUpdate, LocalizedUpdate, FromChatLocalizedUpdate
