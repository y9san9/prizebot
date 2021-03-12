package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.actors.storage.language_codes_storage.LanguageCodesStorage
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.extensions.text.text
import me.y9san9.telegram.updates.hierarchies.FromChatBotUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.FromChatUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate
import me.y9san9.telegram.updates.primitives.LocalizedUpdate


suspend inline fun <T> T.textOrDefault (handler: (String) -> Unit): Boolean where
        T : HasTextUpdate, T : FromChatLocalizedDIBotUpdate<LanguageCodesStorage> =
    text(handler).also { handled ->
        if(!handled)
            sendMessage(locale.enterText)
    }
