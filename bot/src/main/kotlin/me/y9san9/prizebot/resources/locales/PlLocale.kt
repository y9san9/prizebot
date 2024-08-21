package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN

object PlLocale : Locale() {
    override val start = regular("Cześć! Mam na imię ") + bold("PrizeBot") +
            regular(
                ", za pomocą tego bota możesz stworzyć giveaway! " +
                        "Najważniejszą osobliwością bota jest to, że możesz ustawić warunki dla uczestników" +
                        "(subskrypcja na kanał i td). Przypadkowość wyboru jest robiona przez random.org, а "
            ) +
            link(text = "tutajздесь", url = "https://github.com/y9san9/prizebot") + " możesz zobaczyć kod żródłowy\n" +
            "Żeby zapoznać się z innymi możliwośćiami bota, wpisz /help"

    override fun unknownCommand(command: String) = "Niewadoma komenda. '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Oczekiwano $expected argumentów dla komendy, ale $actual znaleziono"

    override val enterText = "Wpisz tekst!"

    override val help = "Hej! Jestem najlepszym botem dla stworzenia giveaway, tutaj masz listę dostępnych komend:\n" +
            "- /help: Odesłać to powiadomienie\n" +
            "- /giveaway: Stworzyć nowe losowanie\n" +
            "- /my_giveaways: Zobaczyć stworzone loterie\n" +
            "- /language: Ustalić język bota\n"

    override val helpKeyboard = "${Emoji.HELP} Pomoc"
    override val giveawayKeyboard = "${Emoji.GIFT} Nowy giveaway"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Moje loterie"

    override val giveawayTitleInput =
        "Dobrze, zaczniemy od stworzenia losowania! Wpisz jaką nazwę chcesz dać (Wciśnij /cancel żeby skasować)"
    override val giveawayParticipateInput =
        "świetnie! Odeślij tekst dla przycisku uczestnika (Wciśnij /cancel żeby skasować lub /skip dla wykorzystania ${Emoji.HEART} domyślnie)"

    override val cancel = "Wrócić"
    override val cancelled = "Skasowano!"

    override val skip = "Pominąć"

    override val giveawayCreated = "Losowanie stworzono, poniżej można zobaczyć demonstrację"

    override val giveawayParticipateHint = "Żeby brać udział w konkursie, wciśnij poniższy przycisk"

    override val giveawayTitleTooLong =
        "Maksymalna długość nazwy konkursu - $MAX_TITLE_LEN symbolów, spróbuj jeszcze raz "

    override val send = "Wysłać..."

    override fun participateText(text: String) = "przycisk uczestnictwa: $text"

    override val cannotParticipateInSelfGiveaway = "Nie możesz brać udział w swoim konkursie :("

    override val nowParticipating = "Bierzesz teraz udział w losowaniu!"

    override val youHaveLeftGiveaway = "Opuściłeś losowanie"

    override val alreadyParticipating = "Bierzesz udział w losowaniu"

    override val highLoadMessage = "Bot jest obecnie bardzo obciążony, prosimy o cierpliwość " +
            "Jeśli jeszcze nie napisałeś do bota, napisz /start do niego, bot napisze o statusie później"

    override val selectGiveawayToView = "Wybierz losowanie, aby zobaczyć więcej"

    override val noGiveawaysYet =
        "Nie utworzyłeś jeszcze żadnego losowania. Można go utworzyć za pomocą komendy /giveaway"

    override val switchPmNoGiveawaysYet = "Kliknij, aby utworzyć swoje pierwsze losowanie"

    override val delete = "Usuwać ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Konkurs '") +
            bold(title.awesomeCut(maxLength = 30)) + "' został usunięty"

    override val thisGiveawayDeleted = "Ten konkurs został usunięty."

    override val raffle = "Zacznij losowanie ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if (plural) "Zwycięzcy" else "Zwycięzca"

    override val deletedUser = "Użytkownik usunięty"

    override fun unknownUser(userId: Long) = "Nieznany użytkownik ($userId)"

    override val participantsCountIsNotEnough =
        "Nie ma wystarczającej liczby uczestników, aby wybrać zwycięzców!"

    override val giveawayFinished = "Losowanie już się zakończyło!"

    override val giveawayDoesNotExist = "To losowanie nie istnieje."

    override val selectLocale = "Wybierz język bota za pomocą poniższych przycisków"

    override val localeSelected = "Zmieniono język"

    override fun confirmation(confirmationText: String) = "Jesteś pewny że chcesz $confirmationText?"

    override val confirm = "Potwierdzać"

    override val deleteGiveawayConfirmation = "usuń losowanie"

    override val raffleGiveawayConfirmation = "wylosować nagrodę"

    override val enterRaffleDateInput = regular("Wprowadź datę automatycznego losowania w jednym z formatów: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (użyj /skip, aby pominąć i /cancel, aby anulować). " +
            "Przesunięcie czasowe można wybrać w następnym kroku."

    override val invalidDateFormat = "Nieprawidłowy format daty, spróbuj ponownie"

    override val selectOffset = "Wybierz przesunięcie czasowe za pomocą przycisków"

    override val customTimeOffset = "Kolejne przesunięcie czasowe"

    override val `UTC-4` = "Nowy Jork -4"
    override val GMT = "Greenwicz +0"
    override val UTC1 = "Berlin +1"
    override val UTC2 = "Kijów +2"
    override val UTC3 = "Moskwa +3"
    override val UTC5_30 = "Indie +5:30"
    override val UTC8 = "Pekin +8"
    override val UTC9 = "Tokio +9"

    override val customTimezoneInput = regular("Wprowadź strefę czasową w jednym z formatów: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Nieprawidłowy format strefy czasowej, spróbuj ponownie"

    override val raffleDate = "Data konkursu"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Za mało uczestników do automatycznej gry '$giveawayTitle', można to zrobić ręcznie później"

    override val winnersCountIsOutOfRange =
        "Liczba zwycięzców musi mieścić się w przedziale od 1 do 50 000"

    override val winnersCount = "Liczba zwycięzców"

    override val enterNumber = "Proszę wpisać numer"

    override val enterWinnersCount =
        "Wprowadź liczbę zwycięzców (użyj /skip, aby domyślnie ustawić 1) lub /cancel, aby anulować)"

    override val chooseConditions =
        "Teraz wybierz warunki uczestnictwa (użyj /next, aby utworzyć losowanie bez warunków lub /cancel, aby anulować)"

    override val chooseMoreConditions =
        "Wybierz następny warunek (użyj /next, aby utworzyć losowanie lub /cancel, aby anulować)"

    override val invitations = "Zaprosić przyjaciół"

    override val channelSubscription = "Dołącz do kanału"

    override val youHaveAlreadyAddedInvitations = "Dodałeś już warunek zapraszania znajomych!"

    override val enterInvitationsCount = "Podaj liczbę zaproszeń do udziału"

    override val selectLinkedChat =
        "Wybierz podłączony kanał (użyj /help, aby dowiedzieć się, jak powiązać kanał, lub /cancel, aby anulować bieżący krok)"

    override val updateChannels = "Zaktualizuj połączone kanały"

    override val channelsUpdated = "Zaktualizowano!"

    override val channelLinkingHelp = bold("Aby połączyć kanał/czat, wykonaj następujące kroki:\n\n") +
            "• Dodaj @y9prizebot do swojego kanału za pomocą " + bold("username") + ", którą chcesz związać (aby każdy mógł do niej dołączyć), " +
            "później zostanie to użyte do sprawdzenia poprawności członków\n" +
            "• Kliknij przycisk aktualizacji\n\n" +
            "\n" +
            "Jeśli bot jest już w kanale, ale nie widzisz kanału na liście, usuń i dodaj bota ponownie."

    override val channelIsNotLinked = "Ten kanał nie jest połączony"

    override val channelIsAlreadyInConditions = "Ten kanał jest już w warunkach uczestnictwa"

    override val giveawayConditions = "Warunki uczestnictwa:"

    override fun subscribeTo(username: String) = regular("Subskrybować ") + bold(username)

    override fun inviteFriends(count: Int) =
        regular("Zaprosić ") + bold("$count") + " ${getValidFriendsForm(count)} w losowanie"

    private fun getValidFriendsForm(count: Int) = when {
        count % 10 == 1 -> "przyjaciela"
        else -> "przyjacieli"
    }

    override val channelConditionRequiredForInvitations =
        "Dodaj co najmniej jedną subskrypcję kanału, aby korzystać z zaproszeń"

    override val invitationsCountShouldBePositive = "Liczba zaproszeń musi być większa od zera"

    override val giveawayInvalid = "Skontaktuj się z organizatorami, konkurs jest nieprawidłowy"

    override val notSubscribedToConditions = "Nie subskrybujesz wszystkich kanałów/czatów"

    override val cannotMentionsUser =
        "Zezwól w ustawieniach na „Przekazywanie wiadomości”, w przeciwnym razie bot nie będzie mógł Ci wspomnieć (ustawienie zostanie zastosowane za 5 minut)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) =
        "Zaprosiłeś $invitedCount / $requiredCount przyjaciół"

    override val raffleProcessing = "Proszę czekać, losowanie jest przetwarzane"

    override val promoteBot = "Zmień bota na administratora"

    override val thisChatIsNotPublic = "Ustaw czat jako publiczny, aby go połączyć"

    override val displayWinnersWithEmoji = "Pokaż zwycięzców za pomocą emotikonów? Na przykład:\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Ta opcja jest dostępna tylko w przypadku losowań, w których bierze udział od 2 do 10 zwycięzców włącznie"

    override val yes = "Tak"

    override val no = "Nie"
}