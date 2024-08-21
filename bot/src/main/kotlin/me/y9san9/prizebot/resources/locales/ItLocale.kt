@file:Suppress("PropertyName")

package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.extensions.string.awesomeCut

object ItLocale : Locale() {
    override val start = regular("Ciao! il mio nome è ") + bold("PrizeBot") +
            regular(
                ", qui potrai sorteggiare casualmente, ed in modo trasparente, premi tra utenti. " +
                        "Una delle caratteristiche principali è che puoi " +
                        "scegliere condizioni per i membri " +
                        "(devono essere iscritti a dei canali, etc.). " +
                        "\nLa randomicità del bot è fornita da random.org e "
            ) + link(text = "lì", url = "https://github.com/y9san9/prizebot") +
            " puoi controllare le fonti.\n\n" +
            "Per visualizzare tutti i comandi disponibili, usa /help"

    override val help = "Hey! Sono un avanzato bot per Giveaway (Regali), ecco la lista dei comandi disponibili:\n" +
            "- /start: Mi attiva! L'hai probabilmente già usato\n" +
            "- /help: Manda questo messaggio\n" +
            "- /giveaway: Crea un nuovo Giveaway\n" +
            "- /my_giveaways: Mostra la lista dei Giveaway creati\n" +
            "- /language: Seleziona la lingua del bot\n"

    override fun unknownCommand (command: String) = "Comando sconosciuto '$command'"

    override fun invalidArgsCount (expected: Int, actual: Int) =
        "Previsti $expected argomenti per il comando, ma ne sono stati trovati $actual"

    override val enterText = "Inserisci il testo!"

    override val helpKeyboard = "${Emoji.HELP} Aiuto"
    override val giveawayKeyboard = "${Emoji.GIFT} Nuovo Giveaway"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} I miei Giveaway"

    override val giveawayTitleInput = "Okay, iniziamo a creaare un nuovo Giveaway, per prima cosa inviami il suo titolo (usa /cancel per cancellare)"
    override val giveawayParticipateInput = "Ottimo! Ora invia il testo del pulsante per partecipare (usa /cancel per cancellare o /skip per usare il predefinito: ${Emoji.HEART})"

    override val cancel = "Cancella"
    override val cancelled = "Cancellato!"

    override val skip = "Salta"

    override val giveawayCreated = "Il tuo Giveaway è stato creato, di seguito puoi trovare il messaggio di esempio"

    override val giveawayParticipateHint = "Per partecipare al Giveaway, premi il pulsante qui sotto."

    override val giveawayTitleTooLong = "La lunghezza massima permessa nel titolo è di $MAX_TITLE_LEN caratteri, per favore prova di nuovo"

    override val send = "Invio..."

    override fun participateText(text: String) = "Pulsante per partecipare: $text"

    override val cannotParticipateInSelfGiveaway = "Non puoi partecipare nel tuo stesso Giveaway!"

    override val nowParticipating = "Stai partecipando a questo Giveaway!"

    override val youHaveLeftGiveaway = "Non stai più partecipando"

    override val alreadyParticipating = "Stai già partecipando a questo Giveaway!"

    override val selectGiveawayToView = "Seleziona il Giveaway dalla lista sottostante"

    override val highLoadMessage = "Il bot in questo momento sta ricevendo un carico elevato di informazioni, quindi " +
            "sii paziente. Scrivi /start al bot se non l'hai ancora fatto, in modo che il bot possa informarti sul tuo stato."

    override val noGiveawaysYet = "Non hai creato alcun Giveaway. Creane uno con il comando /giveaway"

    override val switchPmNoGiveawaysYet = "Premi per creare il tuo primo Giveaway"

    override val delete = "Cancella ${Emoji.TRASH}"

    // FIXME: remove business logic
    override fun giveawayDeleted(title: String) = regular("Giveaway '") +
            bold(title.awesomeCut(maxLength = 30)) + "' cancellato"

    override val thisGiveawayDeleted = "Questo Giveaway è stato cancellato."

    override val raffle = "Sorteggia ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if(plural) "Vincitori" else "Vincitore"

    override val deletedUser = "Utente cancellato"

    override fun unknownUser(userId: Long) = "Utente sconosciuto ($userId)"

    override val participantsCountIsNotEnough =
        "Non ci sono abbastanza partecipanti per sorteggiare!"

    override val giveawayFinished = "Il Giveaway è già terminato!"

    override val giveawayDoesNotExist = "Il Giveaway non esiste"

    override val selectLocale = "Seleziona la lingua del bot con i pulsanti qui sotto"

    override val localeSelected = "Lingua selezionata!"

    override fun confirmation(confirmationText: String) = "Sei sicuro di $confirmationText"

    override val confirm = "Conferma"

    override val deleteGiveawayConfirmation = "Cancella Giveaway"

    override val raffleGiveawayConfirmation = "Sorteggia Giveaway"

    override val enterRaffleDateInput = regular("Inserisci la data per il sorteggio automatico in uno dei seguenti formati: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (usa /skip per saltare o /cancel per cancellare). " +
            "Puoi scegliere il fuso orario nel prossimo passaggio."

    override val invalidDateFormat = "Formato della data invalido, prova ancora"

    override val selectOffset = "Seleziona il fuso orario con i pulsanti qui sotto"

    override val customTimeOffset = "Fuso orario personalizzato"

    override val `UTC-4` = "New York -4"
    override val GMT = "GMT +0"
    override val UTC1 = "Berlino +1"
    override val UTC2 = "Kiev +2"
    override val UTC3 = "Mosca +3"
    override val UTC5_30 = "India +5:30"
    override val UTC8 = "Pechino +8"
    override val UTC9 = "Tokyo +9"

    override val customTimezoneInput = regular("Inserisci il fuso orario personalizzato in uno dei seguenti formati: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Formato fuso orario invalido, prova ancora"

    override val raffleDate = "Data Sorteggio"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Non è possibile sorteggiare automaticamente il Giveaway '$giveawayTitle' per la mancanza di partecipanti, potrai successivamente sorteggiare manualmente"

    override val winnersCountIsOutOfRange = "A causa della lunghezza dei messaggi di Telegram, il numero dei vincitori può variare da 1 a 50"

    override val enterNumber = "Per favore, inserisci un numero"

    override val enterWinnersCount = "Inserisci il numero di vincitori del Giveaway (usa /skip per impostare il valore predefinito (1) o /cancel per annullare)"

    override val winnersCount = "Quantità di vincitori"

    override val chooseConditions = "Ora puoi scegliere le condizioni per i membri del Giveaway (usa /next per creare un Giveaway senza condizioni o /cancel per cancellare)"

    override val chooseMoreConditions = "Seleziona la prossima condizione (usa /next per creare il Giveaway o /cancel per cancellare)"

    override val invitations = "Invita amici"

    override val channelSubscription = "Entra nel canale"

    override val youHaveAlreadyAddedInvitations = "Hai già aggiunto la condizione per gli inviti!"

    override val enterInvitationsCount = "Inserisci il numero di inviti per partecipare"

    override val selectLinkedChat = "Seleziona la chat che hai collegato con i pulsanti qui di sotto (usa /help per aiuto nel collegare un canale, o usa /cancel per cancellare questo passaggio)"

    override val updateChannels = "Aggiorna canali collegati"

    override val channelsUpdated = "Canali collegati aggiornati!"

    override val channelLinkingHelp = bold("Per collegare un canale devi seguire questi passaggi:\n\n") +
            "• Aggiungi come amministratore @y9prizebot al canale/chat che vuoi collegare (" + bold("deve avere un username") + " così che tutti possano entrarvi), " +
            "sarà successivamente utilizzato per controllare se il membro è entrato\n" +
            "• Poi clicca il pulsante per aggiornare e seleziona il canale\n\n" +
            bold("Nel caso il bot sia già nella chat:\n\n") +
            "\n" +
            "Se il bot è già nel canale, ma non lo vedi nella lista, riaggiungi il bot"

    override val channelIsNotLinked = "Questo canale non è collegato!"

    override val channelIsAlreadyInConditions = "Questo canale è già nelle condizioni"

    override val giveawayConditions = "Condizione del Giveaway:"

    override fun subscribeTo(username: String) = regular("Iscriviti a ") + bold(username)

    override fun inviteFriends(count: Int) = regular("Invita ") + bold("$count") + " amic" +
            (if(count > 1) "i" else "o") + " nel Giveaway"

    override val channelConditionRequiredForInvitations = "Almeno un'iscrizione ad un canale è necessaria se vuoi aggiungere gli " +
            "inviti ad amici"

    override val invitationsCountShouldBePositive = "La quantità di inviti deve essere un numero positivo"

    override val giveawayInvalid = "Contatta l'organizzatore perchè il Giveaway sembra invalido"

    override val notSubscribedToConditions = "Non sei entrato in tutte le chat/canali"

    override val cannotMentionsUser = "Attenzione, per partecipare, il bot ha bisogno che tu abbia attiva l'opzione privacy \"Inoltro dei messaggi\"\n" +
            "Per attivarla, vai nelle impostazioni Telegram > \"Privacy\" > \"Inoltro messaggi\" > \"Tutti\""

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Hai invitato $invitedCount / $requiredCount amici"

    override val raffleProcessing = "Per favore attendi, il sorteggio è in elaborazione"

    override val promoteBot = "Devi prima promuovere il bot ad admin"

    override val thisChatIsNotPublic = "Questa chat non è pubblica, per favore aggiungi un username"

    override val displayWinnersWithEmoji = "Vuoi mostrare i vincitori con delle emoji così?\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Questo è disponibile solo per Giveaway con 2 fino a 10  vincitori"

    override val yes = "Sì"

    override val no = "No"
}
