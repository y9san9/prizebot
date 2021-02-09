package me.y9san9.prizebot.logic.database

import com.kotlingang.kds.KDataStorage
import me.y9san9.prizebot.models.Giveaway


internal object KDS : KDataStorage(name = "storage") {
    val giveaways by property(mutableListOf<Giveaway>())
    val participants by property(mutableListOf<Participant>())
}
