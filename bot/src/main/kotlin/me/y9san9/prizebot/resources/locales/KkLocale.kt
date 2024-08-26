package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN

object KkLocale : Locale() {
    override val start = regular("Сәлем! менін атым ") + bold("PrizeBot") +
            regular(", осы жерде сіз жүлделерді ашық түрде ойнай аласыз. " +
                    "Боттың басты ерекшелігі - сіз мүшелер үшін шарттарды таңдай аласыз " +
                    "(Арнаға жазылу және т.б.). Таңдау кездейсоқтығы random.org'пен жеткізіледі, ал ") +
            link(text = "осында", url = "https://github.com/y9san9/prizebot") + " бастапқы кодты көруге болады\n" +
            "Барлық қол жетімді командалардың тізімін көру үшін /help пайдаланыңыз"

    override fun unknownCommand(command: String) = "Белгісіз команда '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Команда үшін $expected дәлелдер күтілуде, бірақ $actual табылды"

    override val enterText = "Мәтінді енгізіңіз!"

    override val help = "Сәлем! Мен ұтыс ойындарының жетілдірілген ботпын, мұнда қол жетімді командалардың тізімі берілген:\n" +
            "- /help: Бұл хабарламаны жіберу\n" +
            "- /giveaway: Жаңа ұтыс ойынын бастау\n" +
            "- /my_giveaways: Жасалған ұтыс ойындарын қарау\n" +
            "- /language: Бот тілін орнату\n"

    override val helpKeyboard = "${Emoji.HELP} Көмек"
    override val giveawayKeyboard = "${Emoji.GIFT} Жаңа ұтыс ойыны"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Менің ұтыс ойындарым"

    override val giveawayTitleInput = "Жарайды, ұтыс ойынын құруды бастайық, бастау үшін ұтыс атауын жіберіңіз (болдырмау үшін /cancel басыңыз)"
    override val giveawayParticipateInput = "Керемет! Енді қатысу түймесіне мәтін жіберіңіз (болдырмау үшін /cancel басыңыз немесе /skip ${Emoji.HEART} әдепкі бойынша)"

    override val cancel = "Артқа"
    override val cancelled = "Жойылды!"

    override val skip = "Өткізіп жіберу"

    override val giveawayCreated = "Ұтыс ойыны жасалды, төменде демонстрациясы бар хабарламаны көруге болады"

    override val giveawayParticipateHint = "Ұтыс ойынына қатысу үшін төмендегі түймені басыңыз."

    override val giveawayTitleTooLong = "Ұтыс ойынының атауының максималды ұзындығы - $MAX_TITLE_LEN таңбалар, қайталап көріңіз"

    override val send = "Жіберу..."

    override fun participateText(text: String) = "Қатысу түймесі: $text"

    override val cannotParticipateInSelfGiveaway = "Сіз өз байқауыңызға қатыса алмайсыз :("

    override val nowParticipating = "Енді сіз ұтыс ойынына қатысасыз!"

    override val youHaveLeftGiveaway = "Сіз ұтыс ойынынан шықтыңыз"

    override val alreadyParticipating = "Сіз ұтыс ойынына қатысасыз"

    override val highLoadMessage = "Қазір бот үлкен жүктемеде, шыдамды болыңыз. " +
            "Егер сіз ботқа әлі хат жазбаған болсаңыз, оған /start деп жазыңыз, бот кейінірек мәртебе туралы жазады"

    override val selectGiveawayToView = "Толығырақ көру үшін ұтыс ойынын таңдаңыз"

    override val noGiveawaysYet = "Сіз әлі бірде-бір ұтыс ойынын жасаған жоқсыз. Оны /giveaway командасымен арқылы жасауға болады"

    override val switchPmNoGiveawaysYet = "Бірінші ұтыс ойынын жасау үшін басыңыз"

    override val delete = "Жою ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Ұтыс ойын '") +
            bold(title.awesomeCut(maxLength = 30)) + "' жойылды"

    override val thisGiveawayDeleted = "Бұл ұтыс ойыны жойылды"

    override val raffle = "Ойнау ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if(plural) "Жеңімпаздар" else "Жеңімпаз"

    override val deletedUser = "Пайдаланушы жойылды"

    override fun unknownUser(userId: Long) = "Белгісіз пайдаланушы ($userId)"

    override val participantsCountIsNotEnough =
        "Жеңімпаздарды таңдау үшін қатысушылар жеткіліксіз!"

    override val giveawayFinished = "Ұтыс ойыны аяқталды!"

    override val giveawayDoesNotExist = "Мұндай ұтыс ойыны жоқ"

    override val selectLocale = "Төмендегі түймелерді пайдаланып бот тілін таңдаңыз"

    override val localeSelected = "Тіл өзгертілді"

    override fun confirmation(confirmationText: String) = "Сіз $confirmationText алғыңыз келетініне сенімдісіз бе?"

    override val confirm = "Растау"

    override val deleteGiveawayConfirmation = "ұтыс ойынын жою"

    override val raffleGiveawayConfirmation = "жүлдені ойнау"

    override val enterRaffleDateInput = regular("Форматтардың бірінде автоматты түрде ұтыс ойынының күнін енгізіңіз: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (өткізіп жіберу үшін /skip және болдырмау үшін /cancel пайдаланыңыз). " +
            "Уақыт ауыстыруды келесі қадамда таңдауға болады."

    override val invalidDateFormat = "Күн дұрыс емес еңгізілген, қайталап көріңіз"

    override val selectOffset = "Түймелерді пайдаланып уақыт ауыстыруды таңдаңыз"

    override val customTimeOffset = "Басқа уақыт ауыстыру"

    override val `UTC-4` = "Нью-Йорк -4"
    override val GMT = "Гринвич +0"
    override val UTC1 = "Берлин +1"
    override val UTC2 = "Киев +2"
    override val UTC3 = "Мәскеу +3"
    override val UTC5_30 = "Үндістан +5:30"
    override val UTC8 = "Бейжің +8"
    override val UTC9 = "Токио +9"

    override val customTimezoneInput = regular("Форматтардың біріне уақыт белдеуін енгізіңіз: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Уақыт белдеуінің дұрыс емес форматы, қайталап көріңіз"

    override val raffleDate = "Ұтыс күні"

    override fun lackOfParticipants(giveawayTitle: String) =
        "'$giveawayTitle' автоматты түрде ойнауға қатысушылар жеткіліксіз, мұны кейінірек қолмен жасауға болады"

    override val winnersCountIsOutOfRange =
        "Жеңімпаздар саны 1 ден 50 000 ға дейін болуы керек"

    override val winnersCount = "Жеңімпаздар саны"

    override val enterNumber = "Нөмірді енгізіңіз"

    override val enterWinnersCount = "Жеңімпаздар санын енгізіңіз (әдепкі мәнді (1) орнату үшін /skip немесе болдырмау үшін /cancel пайдаланыңыз)"

    override val chooseConditions = "Енді қатысу шарттарын таңдаңыз (шарттарсыз ұтыс ойынын жасау үшін /next немесе бас тарту үшін /cancel пайдаланыңыз)"

    override val chooseMoreConditions = "Келесі шартты таңдаңыз (ұтыс ойынын жасау үшін /next немесе болдырмау үшін /cancel пайдаланыңыз)"

    override val invitations = "Достарыңызды шақырыңыз"

    override val channelSubscription = "Арнаға қосылу"

    override val youHaveAlreadyAddedInvitations = "Сіз достарыңызды шақыратын шартты бұрын қостыңыз!"

    override val enterInvitationsCount = "Қатысуға шақырулар санын енгізіңіз"

    override val selectLinkedChat = "Қосылған арнаны таңдаңыз (білу үшін /help пайдаланыңыз арнаны қалай байлау керек, немесе ағымдағы қадамды болдырмау үшін /cancel)"

    override val updateChannels = "Байланыстырылған арналарды жаңартыңыз"

    override val channelsUpdated = "Жаңартылды!"

    override val channelLinkingHelp = bold("Арнаны/чатты байланыстыру үшін келесі қадамдарды орындау қажет:\n\n") +
            "• @y9prizebot сіз байланыстырғыңыз келетін " + bold("username") + " арнасына қосыңыз (кез келген адам оған қосыла алатындай), " +
            "бұл кейінірек мүшелерді тексеру үшін пайдаланылады\n" +
            "• Жаңарту түймесін басу\n\n" +
            "\n" +
            "Егер бот қазір арнада болса, бірақ тізімде арнаны көрмесеңіз, ботты жойып, қайтадан қосыңыз."

    override val channelIsNotLinked = "Бұл арна байланбаған"

    override val channelIsAlreadyInConditions = "Бұл арна қазірдің өзінде қатысу жағдайында"

    override val giveawayConditions = "Қатысу шарттары:"

    override fun subscribeTo(username: String) = bold(username) + "'ға жазылу"

    override fun inviteFriends(count: Int) = bold("$count") + " ${getValidFriendsForm(count)} ұтыс ойынына шақыру"

    private fun getValidFriendsForm(count: Int) = when {
        count % 10 == 1 -> "досты"
        else -> "достарды"
    }

    override val channelConditionRequiredForInvitations = "Шақыруларды пайдалану үшін кем дегенде бір арна жазылымын қосыңыз"

    override val invitationsCountShouldBePositive = "Шақырулар саны нөлден көп болуы керек"

    override val giveawayInvalid = "Ұйымдастырушылармен байланысыңыз, ұтыс ойыны нақты емес"

    override val notSubscribedToConditions = "Сіз барлық арналарға/чаттарға жазылмадыңыз"

    override val cannotMentionsUser = "Параметрлерде 'хабарламаларды жіберуге' рұқсат етіңіз, әйтпесе бот сізді айта алмайды (параметр 5 минуттан кейін қолданылады)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Сіз $invitedCount / $requiredCount достарды шақырдыңыз"

    override val raffleProcessing = "Күте тұрыңыз, ұтыс ойыны өңделуде"

    override val promoteBot = "Ботты әкімшіге көтеріңіз"

    override val thisChatIsNotPublic = "Оны қосу үшін чатты жалпыға қол жетімді етіңіз"

    override val displayWinnersWithEmoji = "Жеңімпаздарды эмодзимен көрсету керек пе? Мысалы:\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Бұл тек 2 ден 10-ға дейінгі жеңімпаздар саны бар ұтыс ойындары үшін қол жетімді"

    override val yes = "Иә"

    override val no = "Жоқ"

}
