@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.prizebot.database.giveaways_storage

import kotlinx.serialization.Serializable


sealed interface CheckedWinnersCount {
    object OutOfRange : CheckedWinnersCount
}

@JvmInline
@Serializable
value class WinnersCount private constructor(val value: Int) : CheckedWinnersCount  {
    companion object {
        fun create(value: Int): WinnersCount {
            val createTry = createChecked(value)
            require(createTry is WinnersCount)
            return createTry
        }

        /**
         * @param value should be in range from 1 to 50
         */
        fun createChecked(value: Int) = when(value) {
            !in 1..50 -> CheckedWinnersCount.OutOfRange
            else -> WinnersCount(value)
        }
    }
}


sealed interface CheckedWinnersSettings {
    object WinnersCountOutOfRange : CheckedWinnersSettings
}

@Serializable
class WinnersSettings private constructor (
    val winnersCount: WinnersCount,
    val displayWithEmojis: Boolean
): CheckedWinnersSettings {
    companion object {
        fun create(winnersCount: WinnersCount, displayWithEmojis: Boolean = false): WinnersSettings {
            val createTry = createChecked(winnersCount, displayWithEmojis)
            require(createTry is WinnersSettings)
            return createTry
        }
        fun createChecked(winnersCount: WinnersCount, displayWithEmojis: Boolean) = when {
            displayWithEmojis && winnersCount.value !in 2..10 -> CheckedWinnersSettings.WinnersCountOutOfRange
            else -> WinnersSettings(winnersCount, displayWithEmojis)
        }
    }
}
