package me.y9san9.telegram.updates.hierarchies


interface PossiblyFromUserLocalizedDIBotUpdate<out T> : PossiblyFromUserBotUpdate, PossiblyFromUserLocalizedDIUpdate<T>
