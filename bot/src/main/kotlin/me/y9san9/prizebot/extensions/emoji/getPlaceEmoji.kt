package me.y9san9.prizebot.extensions.emoji

import me.y9san9.prizebot.resources.Emoji


/**
 * @param place must be in range from 1 to 10
 */
fun Emoji.getPlaceEmoji(place: Int) = when(place) {
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
