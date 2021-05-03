package me.y9san9.telegram.updates.hierarchies

import me.y9san9.telegram.updates.primitives.FromUserUpdate
import me.y9san9.telegram.updates.primitives.LocalizedUpdate
import me.y9san9.telegram.updates.primitives.PossiblyFromUserUpdate


interface PossiblyFromUserLocalizedDIUpdate<out T> : PossiblyFromUserUpdate, LocalizedUpdate, DIBotUpdate<T>
