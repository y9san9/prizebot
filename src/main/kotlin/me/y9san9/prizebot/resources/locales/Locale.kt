package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.bold
import dev.inmo.tgbotapi.types.MessageEntity.textsources.italic
import dev.inmo.tgbotapi.types.MessageEntity.textsources.link
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import me.y9san9.prizebot.logic.utils.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN


object DefaultLocale : Locale()


// fixme: It was bad solution to allow locale to contain entities, cuz it's like business logic, refactor it, move to shortcuts

open class Locale {
    open val start = "Hi there! My name is *PrizeBot*, " +
            "here you can transparently raffle prizes among random users. " +
            "One of the main features is that you can " +
            "choose conditions for members " +
            "(they must be subscribed on some channels and etc.)\n" +
            "To see all available commands, use /help"

    open val help = "Hey! I am advanced bot for giveaways, here is available commands list:\n" +
            "- /start: Starts me! You've probably already used this.\n" +
            "- /help: Sends this message\n" +
            "- /giveaway: Create new giveaway\n" +
            "- /my_giveaways: Get list of created giveaways\n"

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

    open val giveawayTitleMaxLen = "Max allowed title length is $MAX_TITLE_LEN characters, please try again"

    open val send = "Send..."

    open fun participateButton(text: String) = "Participate button: $text"

    open val cannotParticipateInSelfGiveaway = "You cannot participate in your own giveaway!"

    open val nowParticipating = "You are now participating in this giveaway!"

    open val alreadyParticipating = "You are already participating in this giveaway!"

    open val selectGiveawayToView = "Select giveaway from list below"

    open val noGiveawaysYet = "You have not created any giveaway. Create one with /giveaway command"

    open val switchPmNoGiveawaysYet = "Tap to create your first giveaway"

    open val delete = "Delete ${Emoji.TRASH}"

    open fun giveawayDeleted(title: String) = regular("Giveaway '") +
            bold(title.awesomeCut(maxLength = 30)) + "' deleted"

    open val thisGiveawayDeleted = listOf(italic("This giveaway was deleted."))

    open val raffle = "Raffle ${Emoji.GIFT}"

    open val winner = "Winner"

    open val unknownUserDeletedUsername = "Unknown user (the user deleted his username)"

    open val nobodyIsParticipatingYet = "Nobody is participating in giveaway yet!"

    open val giveawayFinished = "Giveaway already finished!"

    companion object {
        fun with(language: String?) = when(language) {
            "ru" -> RuLocale
            else -> DefaultLocale
        }
    }
}