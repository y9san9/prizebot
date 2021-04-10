@file:Suppress("PropertyName")

package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.*
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.prizebot.extensions.string.awesomeCut


object DefaultLocale : Locale()


open class Locale {
    open val start = regular("Hi there! My name is ") + bold("PrizeBot") +
            regular(
                ", here you can transparently raffle prizes among random users. " +
                        "One of the main features is that you can " +
                        "choose conditions for members " +
                        "(they must be subscribed on some channels, etc.). " +
                        "\nBot's random is powered by random.org and "
            ) + link(text = "there", url = "https://github.com/y9san9/prizebot") +
            " you can check sources.\n\n" +
            "To see all available commands, use /help"

    open val help = "Hey! I am advanced bot for giveaways, here is available commands list:\n" +
            "- /start: Starts me! You've probably already used this\n" +
            "- /help: Sends this message\n" +
            "- /giveaway: Create new giveaway\n" +
            "- /my_giveaways: Get list of created giveaways\n" +
            "- /language: Select bot language\n"

    open fun unknownCommand (command: String) = "Unknown command '$command'"

    open fun invalidArgsCount (expected: Int, actual: Int) =
        "Expected $expected args for command, but $actual found"

    open val enterText = "Enter text!"

    open val helpKeyboard = "${Emoji.HELP} Help"
    open val giveawayKeyboard = "${Emoji.GIFT} New giveaway"
    open val selfGiveawaysKeyboard = "${Emoji.SETTINGS} My giveaways"

    open val giveawayTitleInput = "Okay, let's start creating new raffle, first send me it's title (use /cancel to cancel)"
    open val giveawayParticipateInput = "Nice! Now send participate button text (use /cancel to cancel or /skip to use default ${Emoji.HEART})"

    open val cancel = "Cancel"
    open val cancelled = "Cancelled!"

    open val skip = "Skip"

    open val giveawayCreated = "Your giveaway created, below you can see demo message"

    open val giveawayParticipateHint = "To participate in giveaway, push the button below."

    open val giveawayTitleTooLong = "Max allowed title length is $MAX_TITLE_LEN characters, please try again"

    open val send = "Send..."

    open fun participateText(text: String) = "Participate button: $text"

    open val cannotParticipateInSelfGiveaway = "You cannot participate in your own giveaway!"

    open val nowParticipating = "You are now participating in this giveaway!"

    open val alreadyParticipating = "You are already participating in this giveaway!"

    open val selectGiveawayToView = "Select giveaway from list below"

    open val noGiveawaysYet = "You have not created any giveaway. Create one with /giveaway command"

    open val switchPmNoGiveawaysYet = "Tap to create your first giveaway"

    open val delete = "Delete ${Emoji.TRASH}"

    // FIXME: remove business logic
    open fun giveawayDeleted(title: String) = regular("Giveaway '") +
            bold(title.awesomeCut(maxLength = 30)) + "' deleted"

    open val thisGiveawayDeleted = "This giveaway was deleted."

    open val raffle = "Raffle ${Emoji.GIFT}"

    open fun winner(plural: Boolean) = if(plural) "Winners" else "Winner"

    open val deletedUser = "Deleted user"

    open val participantsCountIsNotEnough =
        "There is not enough participants to raffle!"

    open val giveawayFinished = "Giveaway already finished!"

    open val giveawayDoesNotExist = "Giveaway does not exist"

    open val selectLocale = "Select bot locale with buttons below"

    open val localeSelected = "Locale selected!"

    open fun confirmation(confirmationText: String) = "Are you sure you want to $confirmationText"

    open val confirm = "Confirm"

    open val deleteGiveawayConfirmation = "delete giveaway"

    open val raffleGiveawayConfirmation = "raffle giveaway"

    open val enterRaffleDateInput = regular("Enter auto-raffle date in one of this formats: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (use /skip to skip or /cancel to cancel)"

    open val invalidDateFormat = "Invalid date format, try again"

    open val selectTimezone = "Select timezone with buttons below"

    open val customTimezone = "Custom timezone"

    open val `UTC-4` = "New York -4"
    open val GMT = "GMT +0"
    open val UTC1 = "Berlin +1"
    open val UTC2 = "Kiev +2"
    open val UTC3 = "Moscow +3"
    open val UTC5_30 = "India +5:30"
    open val UTC8 = "Peking +8"
    open val UTC9 = "Tokyo +9"

    open val customTimezoneInput = regular("Enter timezone offset in one of this formats: ") +
            bold("+9") + ", " + bold("-9:30")

    open val invalidTimezoneFormat = "Invalid timezone format, try again"

    open val raffleDate = "Raffle date"

    open fun cannotRaffleGiveaway(title: String) =
        "Cannot automatically raffle giveaway '$title', so now you can raffle it manually later"

    open val winnersCountIsOutOfRange = "Winners count may be from 1 up to 50 000"

    open val enterNumber = "Please, enter number"

    open val enterWinnersCount = "Enter giveaway winners count (use /skip to set default value (1) or /cancel to cancel)"

    companion object {
        fun with(language: String?) = locales
            .firstOrNull { it.code == language }
            ?.locale ?: DefaultLocale

        fun all(getter: (Locale) -> String) = locales
            .map(LocaleModel::locale)
            .map(getter)
            .toSet().toList()
    }
}