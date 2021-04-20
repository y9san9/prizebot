package me.y9san9.prizebot.database.giveaways_storage.conditions_storage

import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.TableConditionsStorage.Conditions.GIVEAWAY_ID
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.TableConditionsStorage.Conditions.INVITATIONS_COUNT
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.TableConditionsStorage.Conditions.SUBSCRIPTION_CHANNEL_ID
import me.y9san9.prizebot.database.giveaways_storage.conditions_storage.TableConditionsStorage.Conditions.SUBSCRIPTION_CHANNEL_USERNAME
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


internal class TableConditionsStorage (
    private val database: Database
) : ConditionsStorage {
    private object Conditions : Table(name = "conditions") {
        val GIVEAWAY_ID = long("giveawayId")
        val INVITATIONS_COUNT = integer("invitationsCount").nullable()
        val SUBSCRIPTION_CHANNEL_ID = long("subscriptionChannelId").nullable()
        val SUBSCRIPTION_CHANNEL_USERNAME = text("subscriptionChannelUsername").nullable()
    }

    init {
        transaction(database) {
            SchemaUtils.create(Conditions)
        }
    }

    override fun addConditions(giveawayId: Long, conditions: GiveawayConditions) = transaction(database) {
        conditions.list.forEach { condition ->
            Conditions.insert {
                it[GIVEAWAY_ID] = giveawayId
                when(condition) {
                    is Condition.Subscription -> {
                        it[SUBSCRIPTION_CHANNEL_ID] = condition.channelId
                        it[SUBSCRIPTION_CHANNEL_USERNAME] = condition.channelUsername
                    }
                    is Condition.Invitations -> it[INVITATIONS_COUNT] = condition.count.int
                }
            }
        }
    }

    override fun loadConditions(giveawayId: Long): GiveawayConditions = transaction(database) {
        Conditions.select { GIVEAWAY_ID eq giveawayId }
            .map { it.toCondition() }
            .wrapGiveawayConditions()
    }

    private fun ResultRow.toCondition(): Condition {
        this[SUBSCRIPTION_CHANNEL_ID]?.let { channelId ->
            return Condition.Subscription (
                channelId,
                channelUsername = this[SUBSCRIPTION_CHANNEL_USERNAME] ?: error("Username must be present"))
        }
        this[INVITATIONS_COUNT]?.let { return Condition.Invitations(it.wrapPositiveInt()) }

        error("This condition is not valid")
    }
}