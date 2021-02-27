package me.y9san9.telegram.updates

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.CommonUser
import dev.inmo.tgbotapi.types.update.ChosenInlineResultUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedBotUpdate
import me.y9san9.telegram.updates.primitives.BotUpdate
import me.y9san9.telegram.updates.primitives.DIUpdate
import me.y9san9.telegram.updates.primitives.HasTextUpdate


class ChosenInlineResultUpdate <DI> (
    override val bot: TelegramBot,
    override val di: DI,
    val update: ChosenInlineResultUpdate
) : DIUpdate<DI>, HasTextUpdate, FromChatLocalizedBotUpdate {
    override val text = update.data.query
    override val chatId = update.data.user.id.chatId
    override val languageCode = (update.data.user as? CommonUser)?.languageCode

    val resultId = update.data.resultId
    val inlineMessage = update.data.inlineMessageId
}
