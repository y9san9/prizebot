package me.y9san9.prizebot.actors.storage.giveaways_storage

import com.kotlingang.kds.mutate
import me.y9san9.prizebot.actors.storage.kds.KDS
import me.y9san9.prizebot.logic.utils.replaceFirst


internal class KDSGiveawaysStorage : GiveawaysStorage {
    override fun getGiveawayById(id: Long) = KDS.giveaways.find { it.id == id }

    override fun saveGiveaway(ownerId: Long, title: String, participateButton: String, languageCode: String?) =
        ActiveGiveaway (
            id = (KDS.giveaways.maxOfOrNull { it.id } ?: 0) + 1,
            ownerId, title, participateButton, languageCode
        ).also { giveaway ->
            KDS.mutate {
                giveaways += giveaway
            }
        }

    override fun finishGiveaway(giveawayId: Long, winnerId: Long) = KDS.giveaways
        .replaceFirst({ it.id == giveawayId }) {
            FinishedGiveaway(it.id, it.ownerId, it.title, it.participateText, it.languageCode, winnerId)
        }

    override fun getUserGiveaways(ownerId: Long, count: Int, offset: Long) =
        offset.toInt().let { offsetInt ->
            KDS.giveaways.reversed()
                .filter { it.ownerId == ownerId }
                .drop(offsetInt)
                .take(count)
        }

    override fun deleteGiveaway(id: Long) = KDS.mutate {
        giveaways.removeIf { it.id == id }
    }
}