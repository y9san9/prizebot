package me.y9san9.prizebot.database.giveaways_storage

import org.jetbrains.exposed.sql.Table


internal object Giveaways : Table(name = "giveaways") {
    val GIVEAWAY_ID = long("id").autoIncrement()
    val GIVEAWAY_OWNER_ID = long("ownerId")
    val GIVEAWAY_TITLE = text("title")
    val GIVEAWAY_PARTICIPATE_BUTTON = text("participateButton")
    val GIVEAWAY_RAFFLE_DATE = text("raffleDate").nullable()
    val GIVEAWAY_LANGUAGE_CODE = text("languageCode").nullable()
    val GIVEAWAY_WINNERS_COUNT = integer("winnersCount")
    val GIVEAWAY_DISPLAY_WINNERS_WITH_EMOJIS = bool("winnersWithEmojis")
}
