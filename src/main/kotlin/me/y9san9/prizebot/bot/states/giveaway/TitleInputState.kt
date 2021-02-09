package me.y9san9.prizebot.bot.states.giveaway

import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.bot.shortcuts.FSMPrizebotState
import me.y9san9.prizebot.bot.shortcuts.PrizebotMessageUpdate
import me.y9san9.prizebot.bot.shortcuts.mainStateCancellation
import me.y9san9.prizebot.bot.shortcuts.textOrDefault
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.prizebot.resources.locales.Locale


object TitleInputState : FSMPrizebotState<Unit> {
    override suspend fun process(data: Unit, event: PrizebotMessageUpdate): FSMStateResult<*> {
        event.textOrDefault { message ->
            return when {
                message.content.text == "/cancel" -> event.mainStateCancellation()
                message.content.text.length > MAX_TITLE_LEN -> {
                    event.sendMessage (
                        text = Locale.with(event.languageCode).giveawayTitleMaxLen
                    )
                    result(TitleInputState)
                }
                else -> {
                    event.sendMessage(
                        text = Locale.with(event.languageCode).giveawayParticipateInput
                    )
                    result(data = GiveawayTitle(message.content.text), ParticipateInputState)
                }
            }
        }

        return result(TitleInputState)
    }
}
