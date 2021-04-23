package me.y9san9.prizebot.handlers.channel_group_messages

import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import me.y9san9.prizebot.actors.telegram.ChatLinkerActor
import me.y9san9.prizebot.extensions.telegram.PrizebotMessageUpdate
import me.y9san9.telegram.updates.extensions.command.command


object ChannelGroupMessagesHandler {
    suspend fun handle(update: PrizebotMessageUpdate) = update.command {
        case("/connect_prizebot") {
            safelyWithoutExceptions {
                update.delete()
            }

            ChatLinkerActor.link(update)
        }
    }
}
