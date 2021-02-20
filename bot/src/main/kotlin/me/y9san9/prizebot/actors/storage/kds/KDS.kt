package me.y9san9.prizebot.actors.storage.kds

import com.kotlingang.kds.KDataStorage
import me.y9san9.prizebot.actors.storage.giveaways_storage.Giveaway
import me.y9san9.prizebot.actors.storage.participants_storage.Participant


internal object KDS : KDataStorage(name = "storage") {
    val giveaways by property(mutableListOf<Giveaway>())
    val participants by property(mutableListOf<Participant>())
}
