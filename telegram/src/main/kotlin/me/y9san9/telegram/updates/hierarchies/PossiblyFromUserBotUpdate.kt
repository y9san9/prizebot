package me.y9san9.telegram.updates.hierarchies

import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.PossiblyFromUserUpdate


interface PossiblyFromUserBotUpdate : PossiblyFromUserUpdate, BotUpdate
