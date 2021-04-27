package me.y9san9.prizebot.extensions.telegram

import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.telegram.updates.extensions.send_message.sendMessage
import me.y9san9.telegram.updates.extensions.text.text
import me.y9san9.telegram.updates.hierarchies.FromUserLocalizedDIBotUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


suspend inline fun <T> T.textOrDefault (handler: (String) -> Unit): Boolean where
        T : HasTextUpdate, T : FromUserLocalizedDIBotUpdate<LanguageCodesStorage> =
    text(handler).also { handled ->
        if(!handled)
            sendMessage(locale.enterText)
    }
