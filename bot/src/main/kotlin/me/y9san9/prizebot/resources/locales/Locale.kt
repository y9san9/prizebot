@file:Suppress("PropertyName")

package me.y9san9.prizebot.resources.locales

import dev.inmo.micro_utils.language_codes.asIetfLanguageCode
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import dev.inmo.tgbotapi.utils.buildEntities
import dev.inmo.tgbotapi.utils.newLine
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.locales.ietf.ignoreDialect

object DefaultLocale : Locale()

open class Locale {
    open val start = buildEntities {
        +"Hi there! My name is " + bold("PrizeBot")
        +", here you can transparently raffle prizes among random users. "
        +"One of the main features is that you can choose conditions for members "
        +"(they must be subscribed on some channels, etc.). "
        +newLine
        +"Bot's random is powered by random.org and "
        +link(text = "there", url = "https://github.com/y9san9/prizebot")
        +" you can check sources."
        +newLine
        +newLine
        +"To see all available commands, use /help"
    }

    open val help = "Hey! I am an advanced bot for giveaways, here is the available commands list:\n" +
            "- /start: Starts me! You've probably already used this\n" +
            "- /help: Sends this message\n" +
            "- /giveaway: Create a new giveaway\n" +
            "- /my_giveaways: Get the list of giveaways created\n" +
            "- /language: Select bot's language\n"

    open fun unknownCommand (command: String) = "Unknown command '$command'"

    open fun invalidArgsCount (expected: Int, actual: Int) =
        "Expected $expected args for command, but $actual found"

    open val enterText = "Enter text!"

    open val helpKeyboard = "${Emoji.HELP} Help"
    open val giveawayKeyboard = "${Emoji.GIFT} New giveaway"
    open val selfGiveawaysKeyboard = "${Emoji.SETTINGS} My giveaways"

    open val giveawayTitleInput = "Okay, let's start creating a new raffle, first, send me its title (use /cancel to cancel)"
    open val giveawayParticipateInput = "Nice! Now send the text for the participate button (use /cancel to cancel or /skip to use the default ${Emoji.HEART})"

    open val cancel = "Cancel"
    open val cancelled = "Cancelled!"

    open val skip = "Skip"

    open val giveawayCreated = "Your giveaway has been created, you can see the demo message below"

    open val giveawayParticipateHint = "To participate in the giveaway, push the button below."

    open val giveawayTitleTooLong = "The maximum title length allowed is $MAX_TITLE_LEN characters, please try again"

    open val send = "Send..."

    open fun participateText(text: String) = "Participate button: $text"

    open val cannotParticipateInSelfGiveaway = "You cannot participate in your own giveaway!"

    open val nowParticipating = "You are now participating in this giveaway!"

    open val youHaveLeftGiveaway = "Now you are not participating"

    open val alreadyParticipating = "You are participating in the giveaway"

    open val selectGiveawayToView = "Select giveaway from list below"

    open val highLoadMessage = "The bot right now is receiving lots of requests, so please be patient. " +
            "Write /start to the bot if you haven't done it yet, so the bot can later notify you about your status"

    open val noGiveawaysYet = "You have not created any giveaways. Create one with the command /giveaway"

    open val switchPmNoGiveawaysYet = "Tap to create your first giveaway"

    open val delete = "Delete ${Emoji.TRASH}"

    // FIXME: remove business logic
    open fun giveawayDeleted(title: String) = regular("Giveaway '") +
            bold(title.awesomeCut(maxLength = 30)) + "' deleted"

    open val thisGiveawayDeleted = "This giveaway has been deleted."

    open val raffle = "Raffle ${Emoji.GIFT}"

    open fun winner(plural: Boolean) = if(plural) "Winners" else "Winner"

    open val deletedUser = "Deleted user"

    open val participantsCountIsNotEnough =
        "There are not enough participants to raffle!"

    open val giveawayFinished = "Giveaway is already finished!"

    open val giveawayDoesNotExist = "Giveaway does not exist"

    open val selectLocale = "Select bot language with the buttons below"

    open val localeSelected = "Language selected!"

    open fun confirmation(confirmationText: String) = "Are you sure you want to $confirmationText"

    open val confirm = "Confirm"

    open val deleteGiveawayConfirmation = "delete giveaway"

    open val raffleGiveawayConfirmation = "raffle giveaway"

    open val enterRaffleDateInput = regular("Enter auto-raffle date in one of these formats: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (use /skip to skip or /cancel to cancel). " +
            "You can choose your timezone in the next step."

    open val invalidDateFormat = "Invalid date format, try again"

    open val selectOffset = "Select a timezone with the buttons below"

    open val customTimeOffset = "Custom timezone"

    open val `UTC-4` = "New York -4"
    open val GMT = "GMT +0"
    open val UTC1 = "Berlin +1"
    open val UTC2 = "Kiev +2"
    open val UTC3 = "Moscow +3"
    open val UTC5_30 = "India +5:30"
    open val UTC8 = "Peking +8"
    open val UTC9 = "Tokyo +9"

    open val customTimezoneInput = regular("Enter a timezone in one of this formats: ") +
            bold("+9") + ", " + bold("-9:30")

    open val invalidTimezoneFormat = "Invalid timezone format, try again"

    open val raffleDate = "Raffle date"

    open fun lackOfParticipants(giveawayTitle: String) =
        "Cannot automatically raffle the giveaway '$giveawayTitle' because it lacks of participants, you can raffle it manually later"

    open val winnersCountIsOutOfRange = "Due to Telegram message length, winners amount may be between 1 and 50"

    open val enterNumber = "Please, enter a number"

    open val enterWinnersCount = "Enter the amount of winners for this giveaway (use /skip to set the default value (1) or /cancel to cancel)"

    open val winnersCount = "Winners amount"

    open val chooseConditions = "Now you can choose the conditions for the members of the giveaway (use /next to create the giveaway without conditions or /cancel to cancel)"

    open val chooseMoreConditions = "Choose the next condition (use /next to create giveaway or /cancel to cancel)"

    open val invitations = "Friend Invitations"

    open val channelSubscription = "Join channel"

    open val youHaveAlreadyAddedInvitations = "You have already added the invitations condition!"

    open val enterInvitationsCount = "Enter the amount of invitations for partecipating"

    open val selectLinkedChat = "Select the linked chat with the buttons below (use /help for knowing how to link a channel, or use /cancel to cancel this step)"

    open val updateChannels = "Update linked channels"

    open val channelsUpdated = "Linked channels updated!"

    open val channelLinkingHelp = bold("To link a channel you should follow these steps:\n\n") +
            "• Add @y9prizebot as administrator to the channel/chat you want to link (" + bold("it must have a username") + " so anyone can join it), " +
            "later it will be used to check if member is joined\n" +
            "• Than just click the update button and select the channel\n\n" +
            bold("In case the bot is already in the chat:\n\n") +
            "\n" +
            "If bot is already in the channel, but you don't see the channel in the list, remove and add again the bot."

    open val channelIsNotLinked = "This channel is not linked!"

    open val channelIsAlreadyInConditions = "This channel is already a condition"

    open val giveawayConditions = "Giveaway conditions:"

    open fun subscribeTo(username: String) = regular("Subscribe to ") + bold(username)

    open fun inviteFriends(count: Int) = regular("Invite ") + bold("$count") + " friend" +
            (if(count > 1) "s" else "") + " in giveaway"

    open val channelConditionRequiredForInvitations = "At least one channel subscription is required if you want to add " +
            "Friend invitations"

    open val invitationsCountShouldBePositive = "Invitation amount should be a positive number"

    open val giveawayInvalid = "Contact the organizer because the giveaway seems to be invalid"

    open val notSubscribedToConditions = "You have not joined all the chats/channels"

    open val cannotMentionsUser = "Please allow the bot to 'Forward Messages' in the settings or the bot can't mention you (The setting will be applied within 5 minutes)"

    open fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "You have invited $invitedCount / $requiredCount friends"

    open val raffleProcessing = "Please wait, the raffle is being processed"

    open val promoteBot = "Promote the bot to admin first"

    open val thisChatIsNotPublic = "This chat is not public, please add an username"

    open val displayWinnersWithEmoji = "Display the winners with emojis like this?\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "This is available only for giveaways with amounts of winners from 2 to 10"

    open val yes = "Yes"

    open val no = "No"

    companion object {
        fun with(language: String?): Locale {
            val ietf = language?.asIetfLanguageCode()

            return locales
                .firstOrNull { it.ietf == ietf?.ignoreDialect() }
                ?.locale ?: DefaultLocale
        }

        fun strings(getter: (Locale) -> String) = locales
            .map(LocaleModel::locale)
            .map(getter)
            .toSet().toList()
    }
}