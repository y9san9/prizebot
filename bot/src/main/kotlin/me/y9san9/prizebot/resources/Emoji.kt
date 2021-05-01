@file:Suppress("MemberVisibilityCanBePrivate")

package me.y9san9.prizebot.resources


object Emoji {
    const val GIFT = "\uD83C\uDF81"
    const val HELP = "\uD83C\uDD98"
    const val HEART = "❤️"
    const val SETTINGS = "⚙️"
    const val TRASH = "\uD83D\uDDD1"
    const val CHECKMARK = "✅"
    const val RUSSIAN = "\uD83C\uDDF7\uD83C\uDDFA"
    const val ENGLISH = "\uD83C\uDDEC\uD83C\uDDE7"
    const val HOURGLASS = "⏳"

    // Places emojis
    const val FIRST_PLACE = "\uD83E\uDD47"
    const val SECOND_PLACE = "\uD83E\uDD48"
    const val THIRD_PLACE = "\uD83E\uDD49"
    const val FOUR = "4⃣"
    const val FIVE = "5⃣"
    const val SIX = "6⃣"
    const val SEVEN = "7⃣"
    const val EIGHT = "8⃣"
    const val NINE = "9⃣"
    const val TEN = "\uD83D\uDD1F"

    /**
     * @param place must be in range from 1 to 10
     */
    fun getPlaceEmoji(place: Int) = when(place) {
        1 -> FIRST_PLACE
        2 -> SECOND_PLACE
        3 -> THIRD_PLACE
        4 -> FOUR
        5 -> FIVE
        6 -> SIX
        7 -> SEVEN
        8 -> EIGHT
        9 -> NINE
        10 -> TEN
        else -> error("Place must be in range from 1 to 10")
    }
}
