package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN

object BeLocale : Locale() {
    override val start = regular("Прывітанне! Мяне клічуць ") + bold("PrizeBot") +
        regular(", тут можна празрыста разыграць прызы. " +
            "Галоўная асаблівасць бота — магчымасць выбіраць умовы для ўдзельнікаў " +
            "(падпіска на канал і інш.). Выпадковасць выбару дасягаецца праз random.org, а ") +
        link(text = "тут", url = "https://github.com/y9san9/prizebot") + " можна паглядзець зыходны код.\n" +
        "Каб пабачыць спіс усіх даступных загадаў, увядзі /help"

    override fun unknownCommand(command: String) = "Невядомы загад '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Для гэтага загада чакаецца $expected аргументаў, але перададзена $actual"

    override val enterText = "Увядзі тэкст!"

    override val help = "Прывітанне! Я прасунуты бот для розыгрышаў, вось спіс даступных загадаў:\n" +
        "- /help: Адправіць гэтае паведамленне\n" +
        "- /giveaway: Пачаць новы розыгрыш\n" +
        "- /my_giveaways: Паглядзець створаныя розыгрышы\n" +
        "- /language: Усталяваць мову бота\n"

    override val helpKeyboard = "${Emoji.HELP} Дапамога"
    override val giveawayKeyboard = "${Emoji.GIFT} Новы розыгрыш"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Мае розыгрышы"

    override val giveawayTitleInput = "Добра, давай пачнем стварэнне розыгрышу. Спачатку, дашлі назву конкурсу (Націсні /cancel каб скасаваць)"
    override val giveawayParticipateInput = "Выдатна! Цяпер дашлі тэкст для кнопкі ўдзелу (Націсні /cancel каб скасаваць, або /skip для выкарыстання ${Emoji.HEART} па змаўчанні)"

    override val cancel = "Скасаваць"
    override val cancelled = "Скасавана!"

    override val skip = "Прапусціць"

    override val giveawayCreated = "Розыгрыш створаны, ніжэй можна паглядзець дэма-паведамленне"

    override val giveawayParticipateHint = "Націсні кнопку ніжэй каб удзельнічаць у розыгрышы."

    override val giveawayTitleTooLong = "Максімальная даўжыня для назвы розыгрышу - $MAX_TITLE_LEN знакаў, паспрабуй зноў"

    override val send = "Даслаць…"

    override fun participateText(text: String) = "Кнопка ўдзелу: $text"

    override val cannotParticipateInSelfGiveaway = "Ты не можаш удзельнічаць у сваім жа розыгрышы :("

    override val nowParticipating = "Цяпер ты ўдзельнічаеш у розыгрышы!"

    override val youHaveLeftGiveaway = "Ты пакінуў розыгрыш"

    override val alreadyParticipating = "Ты ўдзельнічаеш у розыгрышы"

    override val highLoadMessage = "Зараз бот знаходзіцца пад вялікай нагрузкай, трывайся. " +
        "Калі ты яшчэ не пісаў боту, дашлі яму /start, пазней ён адкажа табе"

    override val selectGiveawayToView = "Выберы розыгрыш, каб паглядзець яго падрабязней"

    override val noGiveawaysYet = "Ты яшчэ не стварыў ніводнага розыгрышу. Стварыць розыгрыш можна праз загад /giveaway"

    override val switchPmNoGiveawaysYet = "Націсні, каб стварыць свой першы розыгрыш"

    override val delete = "Выдаліць ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Розыгрыш '") +
        bold(title.awesomeCut(maxLength = 30)) + "' выдалены"

    override val thisGiveawayDeleted = "Гэты розыгрыш выдалены"

    override val raffle = "Разыграць ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if (plural) "Пераможцы" else "Пераможца"

    override val deletedUser = "Карыстальнік выдалены"

    override val participantsCountIsNotEnough =
        "Удзельнікаў недастаткова, каб абраць пераможцаў!"

    override val giveawayFinished = "Розыгрыш скончаны!"

    override val giveawayDoesNotExist = "Такога розыгрышу не існуе"

    override val selectLocale = "Абяры мову бота з дапамогай кнопак ніжэй"

    override val localeSelected = "Мова зменена"

    override fun confirmation(confirmationText: String) = "Ты ўпэўнены, што хочаш $confirmationText?"

    override val confirm = "Пацвердзіць"

    override val deleteGiveawayConfirmation = "выдаліць розыгрыш"

    override val raffleGiveawayConfirmation = "разыграць прыз"

    override val enterRaffleDateInput = regular("Увядзі дату для аўтаматычнага розыгрышу ў адным з фарматаў: ") +
        bold("00:00") + ", " +
        bold("00:00 13.01") + ", " +
        bold("00:00 13.01.2020") + " (націсні /skip каб прапусціць, або /cancel каб скасаваць). " +
        "Зрушэнне ад UTC можна будзе абраць на наступным кроку."

    override val invalidDateFormat = "Няправільны фармат даты, паспрабуй зноў"

    override val selectOffset = "Абяры зрушэнне з дапамогай кнопак ніжэй"

    override val customTimeOffset = "Іншае зрушэнне"

    override val `UTC-4` = "Новы Ёрк -4"
    override val GMT = "Грынвіч +0"
    override val UTC1 = "Берлін +1"
    override val UTC2 = "Кіеў +2"
    override val UTC3 = "Масква +3"
    override val UTC5_30 = "Індыя +5:30"
    override val UTC8 = "Пекін +8"
    override val UTC9 = "Токіа +9"

    override val customTimezoneInput = regular("Увядзі часавы пояс у адным з фарматаў: ") +
        bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Няправільны фармат часавога поясу, паспрабуй зноў"

    override val raffleDate = "Дата розыгрышу"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Недастаткова ўдзельнікаў, каб аўтаматычна разыграць '$giveawayTitle', ты можаш зрабіць гэта сам пазней"

    override val winnersCountIsOutOfRange =
        "Колькасць пераможцаў павінна быць паміж 1 і 50 000"

    override val winnersCount = "Колькасць пераможцаў"

    override val enterNumber = "Калі ласка, увядзі лік"

    override val enterWinnersCount = "Увядзі колькасць пераможцаў (націсні /skip, каб усталяваць значэнне па змаўчанні (1), або /cancel, каб скасаваць)"

    override val chooseConditions = "Цяпер абяры ўмовы ўдзелу (націсні /next, каб стварыць розыгрыш без умоў, або /cancel, каб скасаваць)"

    override val chooseMoreConditions = "Абяры наступную ўмову (націсні /next, каб стварыць розыгрыш, або /cancel, каб скасаваць)"

    override val invitations = "Запрасіць сяброў"

    override val channelSubscription = "Падпісацца на канал"

    override val youHaveAlreadyAddedInvitations = "Ты ўжо дадаў умову на запрашэнне сяброў!"

    override val enterInvitationsCount = "Увядзі колькасць патрэбных для ўдзелу запрашэнняў"

    override val selectLinkedChat = "Абяры злучаны канал (націсні /help, каб даведацца як далучыць канал, або /cancel, каб скасаваць)"

    override val updateChannels = "Абнавіць злучаныя каналы"

    override val channelsUpdated = "Абноўлена!"

    override val channelLinkingHelp = bold("Каб далучыць канал або чат вам патрэбна выканаць наступныя крокі:\n\n") +
        "• Дадаць @y9prizebot у якасці адміністратара да канала/чата, які патрэбна далучыць (" + bold("у яго павінна быць імя") + ", каб кожны мог далучыцца да яго), " +
        "пазней гэта будзе выкарыстана для праверкі ўдзельнікаў\n" +
        "• Націснуць на кнопку 'Абнавіць'\n\n" +
        "\n" +
        "Калі бот ужо знаходзіцца ў канале, але ты ня бачыш канал у спісе, выдалі і дадай бота зноў."

    override val channelIsNotLinked = "Гэты канал не далучаны"

    override val channelIsAlreadyInConditions = "Гэты канал ужо ёсць ва ўмовах удзелу"

    override val giveawayConditions = "Умовы удзелу:"

    override fun subscribeTo(username: String) = regular("Падпісацца на ") + bold(username)

    override fun inviteFriends(count: Int) = regular("Запрасіць ") + bold("$count") + " ${getValidFriendsForm(count)} да ўдзелу"

    private fun getValidFriendsForm(count: Int) = when {
        count % 10 == 1 && count != 11 -> "сябра"
        else -> "сяброў"
    }

    override val channelConditionRequiredForInvitations = "Дадай хоць адну падпіску на канал, каб выкарыстоўваць запрашэнні"

    override val invitationsCountShouldBePositive = "Колькасць запрашэнняў павінна быць больш за нуль"

    override val giveawayInvalid = "Звяжыцеся з арганізатарамі, розыгрыш некарэктны"

    override val notSubscribedToConditions = "Вы не падпісаліся на ўсе каналы або чаты"

    override val cannotMentionsUser = "Дазвольце 'Перасылаць паведамленні' ў наладах Telegram, інакш бот не зможа вас зазначыць (налада прыменіцца праз 5 хвілін)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Вы запрасілі $invitedCount / $requiredCount сяброў"

    override val raffleProcessing = "Пачакай, пакуль ідзе апрацоўка розыгрышу"

    override val promoteBot = "Калі ласка, павысь бота да адміністратара"

    override val thisChatIsNotPublic = "Зрабі чат публічным, каб падключыць яго"

    override val displayWinnersWithEmoji = "Паказваць пераможцаў з эмодзі? Напрыклад:\n\n" +
        "${Emoji.FIRST_PLACE} Foo Bar\n" +
        "${Emoji.SECOND_PLACE} Bar Foo\n" +
        "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
        "Гэта даступна толькі для розыгрышаў з колькасцю пераможцаў ад 2 да 10 ўключна"

    override val yes = "Так"

    override val no = "Не"

}
