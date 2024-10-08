package me.y9san9.prizebot.database.giveaways_storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import me.y9san9.prizebot.actors.giveaway.AutoRaffleActor
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_ID
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_LANGUAGE_CODE
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_OWNER_ID
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_PARTICIPATE_BUTTON
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_RAFFLE_DATE
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_TITLE
import me.y9san9.prizebot.database.giveaways_storage.Giveaways.GIVEAWAY_WINNERS_COUNT
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.ConditionsStorage
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.GiveawayConditions
import me.y9san9.prizebot.database.giveaways_storage.giveaways_patch_storage.GiveawaysPatchStorage
import me.y9san9.prizebot.database.giveaways_storage.participants_storage.ParticipantsStorage
import me.y9san9.prizebot.database.giveaways_storage.winners_storage.WinnersStorage
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.OffsetDateTime


internal class TableGiveawaysStorage (
    private val database: Database,
    private val autoRaffleActor: AutoRaffleActor
) : GiveawaysStorage {

    private val participantsStorage = ParticipantsStorage(database)
    private val winnersStorage = WinnersStorage(database)
    private val conditionsStorage = ConditionsStorage(database)
    private val giveawaysPatchStorage = GiveawaysPatchStorage(database, winnersStorage)

    init {
        transaction(database) {
            SchemaUtils.create(Giveaways)
        }
    }

    override suspend fun getGiveawayById(id: Long): Giveaway? = newSuspendedTransaction(Dispatchers.IO, database) {
        Giveaways.selectAll().where { GIVEAWAY_ID eq id }.firstOrNull()?.toGiveaway()
    }

    override suspend fun saveGiveaway (
        ownerId: Long,
        title: String,
        participateButton: String,
        languageCode: String?,
        raffleDate: OffsetDateTime?,
        winnersSettings: WinnersSettings,
        conditions: GiveawayConditions
    ) = newSuspendedTransaction(Dispatchers.IO, database) {
        Giveaways.insert {
            it[GIVEAWAY_OWNER_ID] = ownerId
            it[GIVEAWAY_TITLE] = title
            it[GIVEAWAY_PARTICIPATE_BUTTON] = participateButton
            it[GIVEAWAY_LANGUAGE_CODE] = languageCode
            it[GIVEAWAY_RAFFLE_DATE] = raffleDate?.toString()
            it[GIVEAWAY_WINNERS_COUNT] = winnersSettings.winnersCount.value
            it[GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS] = winnersSettings.displayWithEmojis
        }
    }.let { data ->
        conditionsStorage.addConditions(data[GIVEAWAY_ID], conditions)
        ActiveGiveaway (
            id = data[GIVEAWAY_ID],
            ownerId, title, participateButton,
            languageCode, raffleDate, participantsStorage,
            giveawaysPatchStorage, conditionsStorage,
            WinnersSettings.create (
                WinnersCount.create(data[GIVEAWAY_WINNERS_COUNT]),
                data[GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS]
            ),
            autoRaffleActor
        )
    }

    override suspend fun getUserGiveaways(ownerId: Long, count: Int, offset: Long) = newSuspendedTransaction(Dispatchers.IO, database) {
        Giveaways.selectAll().where { GIVEAWAY_OWNER_ID eq ownerId }
            .orderBy(GIVEAWAY_ID, order = SortOrder.DESC)
            .limit(n = count, offset = offset)
            .map { it.toGiveaway() }
    }

    override suspend fun getAllGiveaways() = channelFlow<Giveaway> {
        newSuspendedTransaction(Dispatchers.IO, database) {
            Giveaways.selectAll().forEach {
                send(it.toGiveaway())
            }
        }
    }

    override suspend fun getGiveawaysWithRaffleDate() = channelFlow<ActiveGiveaway> {
        newSuspendedTransaction(Dispatchers.IO, database) {
            Giveaways.selectAll().where { GIVEAWAY_RAFFLE_DATE neq null }.forEach {
                val giveaway = it.toGiveaway()
                if (giveaway is ActiveGiveaway) {
                    send(giveaway)
                } else {
                    giveaway.giveawaysPatchStorage.removeRaffleDate(giveaway.id)
                }
            }
        }
    }

    private suspend fun ResultRow.toGiveaway(): Giveaway {
        println("> TableGiveawaysStorage: Before winners check ${this[GIVEAWAY_ID]}")
        val hasWinners = winnersStorage.hasWinners(this[GIVEAWAY_ID])
        println("> TableGiveawaysStorage: After winners check ${this[GIVEAWAY_ID]}")
        return if(hasWinners)
            ActiveGiveaway (
                this[GIVEAWAY_ID],
                this[GIVEAWAY_OWNER_ID],
                this[GIVEAWAY_TITLE],
                this[GIVEAWAY_PARTICIPATE_BUTTON],
                this[GIVEAWAY_LANGUAGE_CODE],
                this[GIVEAWAY_RAFFLE_DATE]?.let(OffsetDateTime::parse),
                participantsStorage, giveawaysPatchStorage, conditionsStorage,
                WinnersSettings.create (
                    WinnersCount.create(this[GIVEAWAY_WINNERS_COUNT]),
                    this[GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS]
                ),
                autoRaffleActor
            )
        else
            FinishedGiveaway (
                this[GIVEAWAY_ID],
                this[GIVEAWAY_OWNER_ID],
                this[GIVEAWAY_TITLE],
                this[GIVEAWAY_PARTICIPATE_BUTTON],
                this[GIVEAWAY_LANGUAGE_CODE],
                this[GIVEAWAY_RAFFLE_DATE]?.let(OffsetDateTime::parse),
                WinnersSettings.create (
                    WinnersCount.create(this[GIVEAWAY_WINNERS_COUNT]),
                    this[GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS]
                ),
                participantsStorage, giveawaysPatchStorage,
                conditionsStorage, winnersStorage
            )
    }
}
