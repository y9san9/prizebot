package me.y9san9.prizebot.handlers.my_chat_member_updated

import dev.inmo.tgbotapi.types.ChatMember.abstracts.AdministratorChatMember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.y9san9.prizebot.actors.telegram.ChatLinkerActor
import me.y9san9.prizebot.extensions.telegram.PrizebotMyChatMemberUpdate


class MyChatMemberUpdateHandler(private val scope: CoroutineScope) {

    fun launchHandle(update: PrizebotMyChatMemberUpdate) = scope.launch {
        handle(update)
    }

    private suspend fun handle(update: PrizebotMyChatMemberUpdate) {
        if(update.oldState !is AdministratorChatMember && update.newState is AdministratorChatMember) {
            ChatLinkerActor.link(update)
        }
    }
}
