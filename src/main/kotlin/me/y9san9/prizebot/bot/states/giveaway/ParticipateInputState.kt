package me.y9san9.prizebot.bot.states.giveaway

import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.result
import me.y9san9.prizebot.bot.shortcuts.*
import me.y9san9.prizebot.bot.states.MainState
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.prizebot.bot.shortcuts.mainMarkup
import me.y9san9.telegram.extensions.userLinkProvider


@Serializable
data class GiveawayTitle (
    val title: String
)

object ParticipateInputState : FSMPrizebotState<GiveawayTitle> {

    override suspend fun process(data: GiveawayTitle, event: PrizebotMessageUpdate): FSMStateResult<*> {
        event.textOrDefault {
            return when(it.content.text) {
                "/cancel" -> event.mainStateCancellation()
                "/skip" -> createGiveaway(event, data.title, participateButton = Emoji.HEART)
                else -> createGiveaway(event, data.title, it.content.text)
            }
        }

        return result(data = data, ParticipateInputState)
    }

    private suspend fun createGiveaway(event: PrizebotMessageUpdate, title: String, participateButton: String): FSMStateResult<*> {
        val giveaway = event.di.giveawaysStorage.saveGiveaway (
            ownerId = event.chatId,
            title = title,
            participateButton = participateButton,
            languageCode = event.languageCode
        )

        event.sendMessage (
            text = Locale.with(event.languageCode).giveawayCreated,
            replyMarkup = mainMarkup(event.di, event.chatId, event.languageCode)
        )

        val content = giveawayContent (
            giveaway,
            event.di.participantsStorage.getParticipantsCount(giveaway.id),
            event.bot.userLinkProvider,
            demo = true
        )
        event.sendMessage(content.entities, replyMarkup = content.replyMarkup)

        return result(MainState)
    }
}
