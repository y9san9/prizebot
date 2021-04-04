package me.y9san9.telegram.updates.hierarchies


interface FromChatLocalizedDIBotUpdate<out T> : FromChatBotUpdate, FromChatLocalizedDIUpdate<T>
