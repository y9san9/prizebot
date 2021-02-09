package me.y9san9.prizebot.logic.database

import com.kotlingang.kds.mutate
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_ID
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_LANGUAGE_CODE
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_OWNER_ID
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_PARTICIPATE_BUTTON
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_TITLE
import me.y9san9.prizebot.logic.database.TableGiveawaysStorage.Giveaways.GIVEAWAY_WINNER_ID
import me.y9san9.prizebot.logic.utils.replaceFirst
import me.y9san9.prizebot.models.ActiveGiveaway
import me.y9san9.prizebot.models.FinishedGiveaway
import me.y9san9.prizebot.models.Giveaway
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


interface GiveawaysStorage {
    fun getById(id: Long): Giveaway?
    fun saveGiveaway(ownerId: Long, title: String, participateButton: String, languageCode: String?): Giveaway
    fun finishGiveaway(giveawayId: Long, winnerId: Long)
    fun getUserGiveaways(ownerId: Long, count: Int = 20, offset: Long = 0): List<Giveaway>
    fun deleteGiveaway(id: Long)
}

@Suppress("FunctionName")
fun GiveawayStorage(database: Database?): GiveawaysStorage =
    if(database == null) KDSGiveawaysStorage() else TableGiveawaysStorage(database)


private class KDSGiveawaysStorage : GiveawaysStorage {
    override fun getById(id: Long) = KDS.giveaways.find { it.id == id }

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
            FinishedGiveaway(it.id, it.ownerId, it.title, it.participateButton, it.languageCode, winnerId)
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

private class TableGiveawaysStorage (
    private val database: Database
) : GiveawaysStorage {
    private object Giveaways : Table(name = "giveaways") {
        val GIVEAWAY_ID = long("id").autoIncrement()
        val GIVEAWAY_OWNER_ID = long("ownerId")
        val GIVEAWAY_TITLE = text("title")
        val GIVEAWAY_PARTICIPATE_BUTTON = text("participateButton")
        val GIVEAWAY_LANGUAGE_CODE = text("languageCode").nullable()
        val GIVEAWAY_WINNER_ID = long("winnerId").nullable()
    }

    init {
        transaction(database) {
            SchemaUtils.create(Giveaways)
        }
    }

    override fun getById(id: Long): Giveaway? = transaction(database) {
        Giveaways.select { GIVEAWAY_ID eq id }.firstOrNull()?.toGiveaway()
    }

    override fun saveGiveaway(ownerId: Long, title: String, participateButton: String, languageCode: String?) =
        transaction(database) {
            Giveaways.insert {
                it[GIVEAWAY_OWNER_ID] = ownerId
                it[GIVEAWAY_TITLE] = title
                it[GIVEAWAY_PARTICIPATE_BUTTON] = participateButton
            }
        }.let { data ->
            ActiveGiveaway (
                id = data[GIVEAWAY_ID],
                ownerId, title, participateButton, languageCode
            )
        }

    override fun finishGiveaway(giveawayId: Long, winnerId: Long) = transaction(database) {
        Giveaways.update({ GIVEAWAY_ID eq giveawayId }) {
            it[GIVEAWAY_WINNER_ID] = winnerId
        }
    }.let { }

    override fun getUserGiveaways(ownerId: Long, count: Int, offset: Long) = transaction(database) {
        Giveaways.select { GIVEAWAY_OWNER_ID eq ownerId }
            .orderBy(GIVEAWAY_ID, order = SortOrder.DESC)
            .limit(n = count, offset = offset)
            .map { it.toGiveaway() }
    }

    override fun deleteGiveaway(id: Long) = transaction(database) {
        Giveaways.deleteWhere { GIVEAWAY_ID eq id }
        return@transaction
    }

    private fun ResultRow.toGiveaway() = if(this[GIVEAWAY_WINNER_ID] == null)
        ActiveGiveaway (
            this[GIVEAWAY_ID],
            this[GIVEAWAY_OWNER_ID],
            this[GIVEAWAY_TITLE],
            this[GIVEAWAY_PARTICIPATE_BUTTON],
            this[GIVEAWAY_LANGUAGE_CODE]
        )
    else
        FinishedGiveaway (
            this[GIVEAWAY_ID],
            this[GIVEAWAY_OWNER_ID],
            this[GIVEAWAY_TITLE],
            this[GIVEAWAY_PARTICIPATE_BUTTON],
            this[GIVEAWAY_LANGUAGE_CODE],
            winnerId = this[GIVEAWAY_WINNER_ID] ?: error("Finished giveaway must contain winnerId")
        )
}
