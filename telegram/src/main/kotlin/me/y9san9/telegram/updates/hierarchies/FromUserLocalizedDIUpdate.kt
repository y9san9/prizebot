package me.y9san9.telegram.updates.hierarchies

import me.y9san9.telegram.updates.primitives.FromUserUpdate
import me.y9san9.telegram.updates.primitives.LocalizedUpdate


interface FromUserLocalizedDIUpdate<out T> : FromUserUpdate, LocalizedUpdate, DIBotUpdate<T>
