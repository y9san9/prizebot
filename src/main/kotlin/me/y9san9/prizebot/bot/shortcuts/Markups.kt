package me.y9san9.prizebot.bot.shortcuts

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.SwitchInlineQueryInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.bot.di.PrizebotDI
import me.y9san9.prizebot.logic.utils.plusIf
import me.y9san9.prizebot.models.FinishedGiveaway
import me.y9san9.prizebot.models.Giveaway
import me.y9san9.prizebot.models.locale
import me.y9san9.prizebot.resources.*
import me.y9san9.prizebot.resources.locales.Locale


fun mainMarkup(di: PrizebotDI, userId: Long, languageCode: String?): KeyboardMarkup {
    val buttons = listOf (
        listOf (
            SimpleKeyboardButton(Locale.with(languageCode).helpKeyboard),
            SimpleKeyboardButton(Locale.with(languageCode).giveawayKeyboard)
        )
    )

    val giveawaysButton = listOf(SimpleKeyboardButton(Locale.with(languageCode).selfGiveawaysKeyboard))
    val hasSelfGiveawaysButton = di.giveawaysStorage.getUserGiveaways(userId).isNotEmpty()

    return ReplyKeyboardMarkup (
        resizeKeyboard = true,
        keyboard = if(hasSelfGiveawaysButton) buttons + listOf(giveawaysButton) else buttons
    )
}

fun giveawayMarkup (
    giveaway: Giveaway,
    participantsCount: Int,
    demo: Boolean = false,
): InlineKeyboardMarkup {
    val locale = giveaway.locale

    val participateText = "${giveaway.participateButton}${if(participantsCount == 0) "" else " $participantsCount"}"

    val finished = giveaway is FinishedGiveaway

    fun participateButtonNoAction() = CallbackDataInlineKeyboardButton (
        participateText,
        callbackData = "$CALLBACK_NO_ACTION"
    )

    return InlineKeyboardMarkup (
        keyboard = if(demo) listOf (
            listOf(participateButtonNoAction()),
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
            listOf(SwitchInlineQueryInlineKeyboardButton (
                text = locale.send,
                switchInlineQuery = "${INLINE_ACTION_SEND_GIVEAWAY}_${giveaway.id}"
            ))
        ) else listOf(listOf (
             if(finished) participateButtonNoAction() else CallbackDataInlineKeyboardButton (
                participateText,
                callbackData = "${CALLBACK_ACTION_PARTICIPATE}_${giveaway.id}"
            )
        )),
    )
}

fun selfGiveawaysMarkup (
    offset: Long,
    giveaways: List<Giveaway>,
    hasBefore: Boolean = offset != 0L,
    hasNext: Boolean
): InlineKeyboardMarkup {

    val giveawaysButtons = giveaways.mapIndexed { i, giveaway -> CallbackDataInlineKeyboardButton(
        text = "${offset + i + 1}",
        callbackData = "${CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL}_${giveaway.id}"
    ) }

    val before = if(hasBefore) listOf(CallbackDataInlineKeyboardButton(
        text = "<",
        callbackData = "${CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL}_btn_${offset - 5}"
    )) else listOf()

    val next = if(hasNext) listOf(CallbackDataInlineKeyboardButton(
        text = ">",
        callbackData = "${CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL}_btn_${offset + 5}"
    )) else listOf()

    return InlineKeyboardMarkup (
        keyboard = listOf(before + giveawaysButtons + next)
    )
}
