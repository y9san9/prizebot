package me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.conditions

import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.UsernameChat
import kotlinx.serialization.Serializable
import me.y9san9.fsm.FSMStateResult
import me.y9san9.fsm.stateResult
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.Condition
import me.y9san9.extensions.any.unit
import me.y9san9.prizebot.extensions.telegram.PrizebotFSMState
import me.y9san9.prizebot.extensions.telegram.PrizebotPrivateMessageUpdate
import me.y9san9.prizebot.extensions.telegram.locale
import me.y9san9.prizebot.extensions.telegram.textOrDefault
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.ConditionInputData
import me.y9san9.prizebot.handlers.private_messages.fsm.states.giveaway.ConditionInputState
import me.y9san9.prizebot.resources.locales.Locale
import me.y9san9.prizebot.resources.markups.selectLinkedChatMarkup
import me.y9san9.telegram.updates.extensions.send_message.sendMessage


@Serializable
data class Channel (
    val id: Long,
    val username: String
)

@Serializable
data class SubscriptionChannelInputData (
    val conditionInputData: ConditionInputData,
    val channels: List<Channel>
)

object SubscriptionChannelInputState : PrizebotFSMState<SubscriptionChannelInputData> {

    override suspend fun process (
        data: SubscriptionChannelInputData,
        event: PrizebotPrivateMessageUpdate
    ): FSMStateResult<*> {
        event.textOrDefault { username ->
            when(username) {
                "/cancel" -> return ConditionInputState(event, data.conditionInputData)
                "/help" -> event.sendMessage (
                    event.locale.channelLinkingHelp
                )
                in Locale.all(Locale::updateChannels) -> {
                    val channels = getUserChannels(event)
                    return stateResult(SubscriptionChannelInputState, data.copy(channels = channels)) {
                        event.sendMessage (
                            event.locale.channelsUpdated,
                            replyMarkup = selectLinkedChatMarkup(event, channels.map(Channel::username))
                        )
                    }
                }
                else -> {
                    val conditions = data.conditionInputData.conditions

                    val usernames = conditions
                        .filterIsInstance<Condition.Subscription>()
                        .map { it.channelUsername }

                    if(username in usernames)
                        return@textOrDefault event.sendMessage(event.locale.channelIsAlreadyInConditions).unit

                    val channel = data.channels.firstOrNull { it.username == username }
                        ?: return@textOrDefault event.sendMessage(event.locale.channelIsNotLinked).unit

                    val newConditions = conditions + Condition.Subscription(channel.id, channel.username)
                    val conditionData = data.conditionInputData.copy (
                        conditions = newConditions
                    )

                    return ConditionInputState(event, conditionData, event.locale.chooseMoreConditions)
                }
            }
        }

        return stateResult(SubscriptionChannelInputState, data)
    }
}


@Suppress("FunctionName")
suspend fun SubscriptionChannelInputState (
    update: PrizebotPrivateMessageUpdate,
    data: ConditionInputData
): FSMStateResult<*> {
    val channels = getUserChannels(update)

    val usernames = channels.map(Channel::username)

    return stateResult(SubscriptionChannelInputState, SubscriptionChannelInputData(data, channels)) {
        update.sendMessage(update.locale.selectLinkedChat, replyMarkup = selectLinkedChatMarkup(update, usernames))
    }
}

private suspend fun getUserChannels(update: PrizebotPrivateMessageUpdate) =
    update.di.getChannels(update.userId)
        .mapNotNull { channelId ->
            val username = try {
                (update.bot.getChat(ChatId(channelId)) as? UsernameChat)?.username?.username
            } catch (_: Exception) { null }
                ?: return@mapNotNull update.di.unlinkChannel(update.userId, channelId).run { null }

            channelId to username
        }.map { (id, username) -> Channel(id, username) }
