@file:Suppress("PropertyName")

package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.extensions.string.awesomeCut

object DeLocale : Locale() {
    override val start = regular("Hallo, mein Name ist ") + bold("PrizeBot") +
            regular(
                ", Hier kannst du transparent Preise unter zufällig ausgewählten Benutzern verlosen. " +
                        "Eines der Hauptmerkmale ist, dass du " +
                        "die Bedingungen für deine Mitglieder wählen kannst. " +
                        "(Diese müssen einige Kanäle abonniert haben, etc.). " +
                        "\nDie zufällige Auswahl des Bots wird durch random.org erstellt und "
            ) + link(text = "dort", url = "https://github.com/y9san9/prizebot") +
            " kannst du die Quellen überprüfen.\n\n" +
            "um alle verfügbaren Befehle zu sehen, verwende /help"

    override val help = "Hey, ich bin ein fortgeschrittener Bot für Verlosungen, hier ist die Liste der verfügbaren Befehle: \n" +
            "- /start: Startet mich! Vermutlich hast du das schon benutzt\n" +
            "- /help: Sendet diese Nachricht\n" +
            "- /giveaway: Erstellt eine neue Verlosung\n" +
            "- /my_giveaways: Erhalte eine Liste der erstellten Verlosungen\n" +
            "- /language: Wähle die Bot-Sprache\n"

    override fun unknownCommand (command: String) = "Unbekannter Befehl '$command'"

    override fun invalidArgsCount (expected: Int, actual: Int) =
        "Erwartete $expected Argumente für den Befehl, aber $actual gefunden"

    override val enterText = "Gib den Text ein!"

    override val helpKeyboard = "${Emoji.HELP} Hilfe"
    override val giveawayKeyboard = "${Emoji.GIFT} Neue Verlosung"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Meine Verlosungen"

    override val giveawayTitleInput = "Okay, beginnen wir mit der Erstellung einer neuen Verlosung. Sende mir zuerst den Titel (benutze /cancel, um abzubrechen)"
    override val giveawayParticipateInput = "Sehr gut! Sende jetzt den Text für den Teilnahmebutton (benutze /cancel um abzubrechen oder /skip um den Standard zu verwenden) ${Emoji.HEART})"

    override val cancel = "abbrechen"
    override val cancelled = "Abgebrochen!"

    override val skip = "überspringen"

    override val giveawayCreated = "Deine Verlosung wurde erstellt, unten siehst du die Demo-Nachricht"

    override val giveawayParticipateHint = "Um an der Verlosung teilzunehmen, drücke auf den Button unten."

    override val giveawayTitleTooLong = "Maximal erlaubte Länge des Titels sind $MAX_TITLE_LEN Zeichen, bitte versuche es nochmals"

    override val send = "Send..."

    override fun participateText(text: String) = "Teilnahmebutton: $text"

    override val cannotParticipateInSelfGiveaway = "Du kannst nicht an deiner eigenen Verlosung teilnehmen!"

    override val nowParticipating = "Du nimmst jetzt an der Verlosung teil!"

    override val youHaveLeftGiveaway = "Jetzt nimmst du nicht teill"

    override val alreadyParticipating = "Du nimmst an der Verlosung teil"

    override val selectGiveawayToView = "Wähle die Verlosung aus der Liste unten aus"

    override val highLoadMessage = "Der Bot ist derzeit stark ausgelastet, also habe bitte etwas Geduld. " +
            "Schreibe /start an den Bot, wenn du es noch nicht getan hast, damit der Bot dich später über deinen Status benachrichtigen kann."

    override val noGiveawaysYet = "Du hast keine Verlosung erstellt. Erstelle eine mit dem /giveaway Befehl"

    override val switchPmNoGiveawaysYet = "Drücke um deine erste Verlosung zu erstellen"

    override val delete = "löschen ${Emoji.TRASH}"

    // FIXME: remove business logic
    override fun giveawayDeleted(title: String) = regular("Giveaway '") +
            bold(title.awesomeCut(maxLength = 30)) + "' löschen"

    override val thisGiveawayDeleted = "Diese Verlosung wurde gelöscht."

    override val raffle = "verlosen ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if(plural) "Gewinner" else "Gewinner"

    override val deletedUser = "Benutzer löschen"

    override val participantsCountIsNotEnough =
        "Es hat nicht genug Teilnehmer für die Verlosung!"

    override val giveawayFinished = "Verlosung bereits beendet!"

    override val giveawayDoesNotExist = "Verlosung existiert nicht"

    override val selectLocale = "Wähle die Bot-Sprache mit den Buttons unten"

    override val localeSelected = "Sprache gewählt!"

    override fun confirmation(confirmationText: String) = "Bist du sicher, du möchtest $confirmationText"

    override val confirm = "bestätigen"

    override val deleteGiveawayConfirmation = "Verlosung löschen"

    override val raffleGiveawayConfirmation = "Verlosung durchführen"

    override val enterRaffleDateInput = regular("Gib das Datum für die automatische Verlosung in einem dieser Formate ein: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (Nutze /skip um zu überspringen oder /cancel um abzubrechen). " +
            "Du kannst eine Zeitzone im nächsten Schritt auswählen."

    override val invalidDateFormat = "Ungültiges Datumsformat, versuche es erneut"

    override val selectOffset = "Wähle die Zeitzone mit dem Button unten aus"

    override val customTimeOffset = "Zeitzone des Kunden"

    override val `UTC-4` = "New York -4"
    override val GMT = "GMT +0"
    override val UTC1 = "Berlin +1"
    override val UTC2 = "Kiev +2"
    override val UTC3 = "Moscow +3"
    override val UTC5_30 = "India +5:30"
    override val UTC8 = "Peking +8"
    override val UTC9 = "Tokyo +9"

    override val customTimezoneInput = regular("Gib die Zeitzone in einem dieser Formate ein: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Ungültiges Zeitzonenformat, versuche es erneut"

    override val raffleDate = "Datum der Verlosung"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Die Verlosung '$giveawayTitle' kann nicht automatisch durchgeführt werden aufgrund zu weniger Teilnehmer, du kannst sie manuell durchführen"

    override val winnersCountIsOutOfRange = "Aufgrund der erlaubten Länge der Telegram Nachricht, die Liste der Gewinner kann bis zu 50 zeigen"

    override val enterNumber = "Bitte gib die Nummer ein"

    override val enterWinnersCount = "Gib die Anzahl der Gewinner ein (nutze /skip für die Standardeinstellung (1) oder /cancel um abzubrechen)"

    override val winnersCount = "Anzahl Gewinner"

    override val chooseConditions = "Jetzt kannst du die Konditionen für die Teilnehmer auswählen (nutze /next um eine Verlosung ohne Konditionen zu erstellen oder /cancel um abzubrechen)"

    override val chooseMoreConditions = "Wähle eine weitere Kondition (nutze /next um eine Verlosung zu erstellen oder /cancel um abzubrechen)"

    override val invitations = "Lade Freunde ein"

    override val channelSubscription = "Schliesse dich dem Kanal an"

    override val youHaveAlreadyAddedInvitations = "Du hast bereits die Einladungen als Kondition hinzugefügt!"

    override val enterInvitationsCount = "Gib die Anzahl Einladungen für die Teilnehmer an"

    override val selectLinkedChat = "Wähle den verlinkten Chat mit den Buttons unten aus (nutze /help um Hilfe zu bekommen beim Verlinken des Kanals oder nutze /cancel um den aktuellen Schritt abzubrechen)"

    override val updateChannels = "Update verlinkte Kanäle"

    override val channelsUpdated = "Verlinkte Kanäle sind auf dem neuesten Stand!"

    override val channelLinkingHelp = bold("Um einen Kanal zu verlinken solltest du diese Schritte befolgen:\n\n") +
            "• Füge @y9prizebot als Administrator dem Kanal/Chat deiner Wahl hinzu (" + bold("Er muss einen Benutzernamen haben") + " damit sich jeder anschliessen kann), " +
            "Später wird dieser benutzt um zu überprüfen ob sich ein neues Mitglied angeschlossen hat\n" +
            "• Dann musst du nur noch den Update Button anklicken und den Kanal auswählen\n\n" +
            bold("Falls der Bot bereits im Chat ist:\n\n") +
            "\n" +
            "Falls der Bot bereits im Kanal ist, du aber den Kanal nicht in der Liste siehst, füge den Bot nochmals hinzu."

    override val channelIsNotLinked = "Dieser Kanal ist nicht verlinkt!"

    override val channelIsAlreadyInConditions = "Der Kanal ist bereits als Kondition festgelegt"

    override val giveawayConditions = "Konditionen der Verlosung:"

    override fun subscribeTo(username: String) = regular("abonnieren ") + bold(username)

    override fun inviteFriends(count: Int) = regular("Lade ") + bold("$count") + " Freund" +
            (if(count > 1) "e" else "") + " zur Verlosung ein"

    override val channelConditionRequiredForInvitations = "Es ist mindestens ein Kanal-Abonnement benötigt, wenn du " +
            "Einladungen für Freunde hinzufügen möchtest"

    override val invitationsCountShouldBePositive = "Die Anzahl der Einladungen sollte eine positive Zahl sein"

    override val giveawayInvalid = "Kontaktiere den Organisator da die Verlosung ungültig zu sein scheint"

    override val notSubscribedToConditions = "Du hast dich nicht allen Chats/Kanälen angeschlossen"

    override val cannotMentionsUser = "Bitte erlaube dem Bot das Weiterleiten der Nachrichten oder er kann dich nicht nennen (die Einstellung wird innerhalb der nächsten 5 Minuten erledigt)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Du hast $invitedCount / $requiredCount Freunde eingeladen"

    override val raffleProcessing = "Bitte warte, die Verlosung wurde gestartet"

    override val promoteBot = "Zuerst befördere den Bot zum Administrator"

    override val thisChatIsNotPublic = "Dieser Chat ist nicht öffentlich, bitte gib deinen Benutzernamen ein"

    override val displayWinnersWithEmoji = "Sollen die Gewinner mit Emojis wie diesen dargestellt werden?\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Dies ist nur für Verlosungen erhältlich mit Gewinneranzahlen zwischen 2 und 10"

    override val yes = "Ja"

    override val no = "Nein"
}