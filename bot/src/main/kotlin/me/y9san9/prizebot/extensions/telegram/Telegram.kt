package me.y9san9.prizebot.extensions.telegram

import me.y9san9.fsm.FSMState
import me.y9san9.fsm.FSMStorage
import me.y9san9.prizebot.database.language_codes_storage.LanguageCodesStorage
import me.y9san9.prizebot.di.PrizebotDI
import me.y9san9.telegram.updates.*
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIBotUpdate
import me.y9san9.telegram.updates.hierarchies.FromChatLocalizedDIUpdate


typealias PrizebotPrivateMessageUpdate = PrivateMessageUpdate<PrizebotDI>
typealias PrizebotInlineQueryUpdate = InlineQueryUpdate<PrizebotDI>
typealias PrizebotChosenInlineResultUpdate = ChosenInlineResultUpdate<PrizebotDI>
typealias PrizebotCallbackQueryUpdate = CallbackQueryUpdate<PrizebotDI>
typealias PrizebotChannelPostUpdate = ChannelPostUpdate<PrizebotDI>
typealias PrizebotFSMStorage = FSMStorage<Long, Any?>
typealias PrizebotFSMState<TDataIn> = FSMState<TDataIn, PrizebotPrivateMessageUpdate>
typealias PrizebotLocalizedUpdate = FromChatLocalizedDIUpdate<LanguageCodesStorage>
typealias PrizebotLocalizedBotUpdate = FromChatLocalizedDIBotUpdate<LanguageCodesStorage>
