package me.y9san9.prizebot.handlers.my_chat_member_updated

import dev.inmo.tgbotapi.types.chat.member.AdministratorChatMember
import me.y9san9.prizebot.actors.telegram.ChatLinkerActor
import me.y9san9.prizebot.extensions.telegram.PrizebotMyChatMemberUpdate

object MyChatMemberUpdateHandler {
    suspend fun handle(update: PrizebotMyChatMemberUpdate) {
        if(update.oldState !is AdministratorChatMember && update.newState is AdministratorChatMember) {
            ChatLinkerActor.link(update)
        }
    }
}
