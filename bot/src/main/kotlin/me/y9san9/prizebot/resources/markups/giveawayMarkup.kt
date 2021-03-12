package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.SwitchInlineQueryInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.actors.storage.giveaways_storage.FinishedGiveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.giveaways_storage.locale
import me.y9san9.prizebot.actors.storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.logic.utils.plusIf
import me.y9san9.prizebot.resources.*
import me.y9san9.telegram.updates.primitives.DIUpdate


fun giveawayMarkup (
    update: DIUpdate<ParticipantsStorage>,
    giveaway: Giveaway,
    demo: Boolean = false
) = giveawayMarkup(update.di.getParticipantsCount(giveaway.id), giveaway, demo)

fun giveawayMarkup (
    participantsCount: Int,
    giveaway: Giveaway?,
    demo: Boolean = false,
): InlineKeyboardMarkup? {
    giveaway ?: return null

    val locale = giveaway.locale

    val participateText = "${giveaway.participateText}${if(participantsCount == 0) "" else " $participantsCount"}"

    val finished = giveaway is FinishedGiveaway

    fun participateButtonUpdateAction() = CallbackDataInlineKeyboardButton (
        participateText,
        callbackData = "${CALLBACK_ACTION_UPDATE_COUNTER}_${giveaway.id}"
    )

    return InlineKeyboardMarkup (
        keyboard = if(demo) listOf (
            listOf(participateButtonUpdateAction()),
            listOf (
                CallbackDataInlineKeyboardButton (
                    text = locale.delete,
                    callbackData = "${CALLBACK_ACTION_DELETE_GIVEAWAY}_${giveaway.id}"
                )
            ).plusIf(!finished) {
                CallbackDataInlineKeyboardButton (
                    text = locale.raffle,
                    callbackData = "${CALLBACK_ACTION_RAFFLE_GIVEAWAY}_${giveaway.id}"
                )
            },
            listOf (
                SwitchInlineQueryInlineKeyboardButton (
                    text = locale.send,
                    switchInlineQuery = "${INLINE_ACTION_SEND_GIVEAWAY}_${giveaway.id}"
                )
            )
        ) else listOf(listOf (
            if(finished) participateButtonUpdateAction() else CallbackDataInlineKeyboardButton (
                participateText,
                callbackData = "${CALLBACK_ACTION_PARTICIPATE}_${giveaway.id}"
            )
        )),
    )
}
