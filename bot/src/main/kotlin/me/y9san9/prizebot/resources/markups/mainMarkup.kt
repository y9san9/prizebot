package me.y9san9.prizebot.resources.markups

import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import me.y9san9.prizebot.actors.storage.giveaways_storage.GiveawaysStorage
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate


fun <TUpdate> mainMarkup(update: TUpdate): KeyboardMarkup where
        TUpdate : DIUpdate<out GiveawaysStorage>, TUpdate : FromChatLocalizedUpdate {
    val storage = update.di
    val userId = update.chatId
    val locale = update.locale

    val buttons = listOf (
        listOf (
            SimpleKeyboardButton(locale.helpKeyboard),
            SimpleKeyboardButton(locale.giveawayKeyboard)
        )
    )

    val giveawaysButton = listOf(SimpleKeyboardButton(locale.selfGiveawaysKeyboard))
    val hasSelfGiveawaysButton = storage.getUserGiveaways(userId).isNotEmpty()

    return ReplyKeyboardMarkup (
        resizeKeyboard = true,
        keyboard = if(hasSelfGiveawaysButton) buttons + listOf(giveawaysButton) else buttons
    )
}
