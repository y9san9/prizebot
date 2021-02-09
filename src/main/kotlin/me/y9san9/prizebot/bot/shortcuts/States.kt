package me.y9san9.prizebot.bot.shortcuts

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.bot.states.MainState
import me.y9san9.prizebot.resources.locales.Locale


suspend fun PrizebotMessageUpdate.mainStateCancellation(): FSMStateResult<*> {
    sendMessage (
        text = Locale.with(languageCode).cancelled,
        replyMarkup = mainMarkup(di, chatId, languageCode)
    )

    return result(MainState)
}
