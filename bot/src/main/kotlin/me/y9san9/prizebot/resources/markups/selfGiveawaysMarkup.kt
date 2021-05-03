package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import me.y9san9.prizebot.database.giveaways_storage.Giveaway
import me.y9san9.prizebot.resources.CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL


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

    val before = if(hasBefore) listOf(
        CallbackDataInlineKeyboardButton(
        text = "<",
        callbackData = "${CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL}_btn_${offset - 5}"
    )
    ) else listOf()

    val next = if(hasNext) listOf(
        CallbackDataInlineKeyboardButton(
        text = ">",
        callbackData = "${CALLBACK_ACTION_SELF_GIVEAWAYS_CONTROL}_btn_${offset + 5}"
    )
    ) else listOf()

    return InlineKeyboardMarkup (
        keyboard = listOf(before + giveawaysButtons + next)
    )
}
