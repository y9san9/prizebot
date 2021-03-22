package me.y9san9.prizebot.actors.storage.language_codes_storage

import me.y9san9.prizebot.actors.storage.kds.KDS


internal class KDSLanguageCodesStorage : LanguageCodesStorage {
    override fun setLanguageCode(userId: Long, languageCode: String) {
        KDS.languageCodes.removeIf { it.userId == userId }
        KDS.languageCodes += LanguageCode(userId, languageCode)
    }

    override fun getLanguageCode(userId: Long) =
        KDS.languageCodes.firstOrNull { it.userId == userId }?.code

    override fun containsLanguageCode(userId: Long) =
        KDS.languageCodes.firstOrNull { it.userId == userId } != null
}
