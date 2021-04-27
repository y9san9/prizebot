package me.y9san9.telegram.updates.hierarchies


interface FromUserLocalizedDIBotUpdate<out T> : FromUserBotUpdate, FromUserLocalizedDIUpdate<T>
