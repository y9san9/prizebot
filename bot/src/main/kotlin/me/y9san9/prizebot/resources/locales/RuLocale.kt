package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN

object RuLocale : Locale() {
    override val start = regular("Привет! Меня зовут ") + bold("PrizeBot") +
            regular(", здесь вы можете прозрачно разыграть призы. " +
                    "Главная особенность бота - вы можете выбирать условия для участников " +
                    "(подписка на канал и пр.). Случайность выбора достигается с помощью random.org, а ") +
                    link(text = "здесь", url = "https://github.com/y9san9/prizebot") + " можно посмотреть исходный код\n" +
                    "Чтобы посмотреть список всех доступных команд, используйте /help"

    override fun unknownCommand(command: String) = "Неизвестная команда '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Ожидается $expected аргументов для команды, но $actual найдено"

    override val enterText = "Введите текст!"

    override val help = "Привет! Я продвинутый бот для розыгрышей, вот список доступных команд:\n" +
            "- /help: Отправить это сообщение\n" +
            "- /giveaway: Начать новый розыгрыш\n" +
            "- /my_giveaways: Посмотреть созданные розыгрыши\n" +
            "- /language: Установить язык бота\n"

    override val helpKeyboard = "${Emoji.HELP} Помощь"
    override val giveawayKeyboard = "${Emoji.GIFT} Новый розыгрыш"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Мои розыгрыши"

    override val giveawayTitleInput = "Хорошо, давай начнём создание розыгрыша, для начала отправь название конкурса (Нажми /cancel чтобы отменить)"
    override val giveawayParticipateInput = "Отлично! Теперь отправь текст для кнопки участия (Нажми /cancel чтобы отменить или /skip для использования ${Emoji.HEART} по умолчанию)"

    override val cancel = "Назад"
    override val cancelled = "Отменено!"

    override val skip = "Пропустить"

    override val giveawayCreated = "Розыгрыш создан, ниже можно посмотреть сообщение с демонстрацией"

    override val giveawayParticipateHint = "Чтобы участвовать в розыгрыше, нажми кнопку ниже."

    override val giveawayTitleTooLong = "Максимальная длина для названия розыгрыша - $MAX_TITLE_LEN символов, попробуй ещё раз"

    override val send = "Отправить..."

    override fun participateText(text: String) = "Кнопка участия: $text"

    override val cannotParticipateInSelfGiveaway = "Вы не можете участвовать в своём конкурсе :("

    override val nowParticipating = "Теперь ты участвуешь в розыгрыше!"

    override val youHaveLeftGiveaway = "Вы вышли из розыгрыша"

    override val alreadyParticipating = "Ты участвуешь в розыгрыше"

    override val selectGiveawayToView = "Выбери розыгрыш, чтобы посмотреть подробнее"

    override val noGiveawaysYet = "Ты ещё не создал ни одного розыгрыша. Его можно создать с помощью команды /giveaway"

    override val switchPmNoGiveawaysYet = "Нажмите, чтобы создать свой первый розыгрыш"

    override val delete = "Удалить ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Розыгрыш '") +
            bold(title.awesomeCut(maxLength = 30)) + "' удалён"

    override val thisGiveawayDeleted = "Этот розыгрыш удалён"

    override val raffle = "Разыграть ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if(plural) "Победители" else "Победитель"

    override val deletedUser = "Пользователь удалён"

    override val participantsCountIsNotEnough =
        "Участников недостаточно, чтобы выбрать победителей!"

    override val giveawayFinished = "Розыгрыш уже закончен!"

    override val giveawayDoesNotExist = "Такого розыгрыша не существует"

    override val selectLocale = "Выберите язык бота с помощью кнопок ниже"

    override val localeSelected = "Язык изменён"

    override fun confirmation(confirmationText: String) = "Вы уверены, что хотите $confirmationText?"

    override val confirm = "Подтвердить"

    override val deleteGiveawayConfirmation = "удалить розыгрыш"

    override val raffleGiveawayConfirmation = "разыграть приз"

    override val enterRaffleDateInput = regular("Введите дату для автоматического розыгрыша в одном из форматов: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (используйте /skip, чтобы пропустить, и /cancel, чтобы отменить). " +
            "Смещение времени можно будет выбрать на следующем шаге."

    override val invalidDateFormat = "Неверный формат даты, попробуйте ещё раз"

    override val selectOffset = "Выберите смещение времени с помощью кнопок"

    override val customTimeOffset = "Другое смещение"

    override val `UTC-4` = "Нью-Йорк -4"
    override val GMT = "Гринвич +0"
    override val UTC1 = "Берлин +1"
    override val UTC2 = "Киев +2"
    override val UTC3 = "Москва +3"
    override val UTC5_30 = "Индия +5:30"
    override val UTC8 = "Пекин +8"
    override val UTC9 = "Токио +9"

    override val customTimezoneInput = regular("Введите часовой пояс в одном из форматов: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Неправильный формат часового пояса, попробуйте ещё раз"

    override val raffleDate = "Дата розыгрыша"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Недостаточно участников, чтобы автоматически разыграть '$giveawayTitle', вы можете сделать это вручную позже"

    override val winnersCountIsOutOfRange =
        "Количество победителей должно быть от 1 до 50 000"

    override val winnersCount = "Количество победителей"

    override val enterNumber = "Пожалуйста, ведите число"

    override val enterWinnersCount = "Введите количество победителей (используйте /skip, чтобы установить значение по умолчанию (1), или /cancel, чтобы отменить)"

    override val chooseConditions = "Теперь выберите условия участия (используйте /next, чтобы создать розыгрыш без условий, или /cancel, чтобы отменить)"

    override val chooseMoreConditions = "Выберите следующее условие (используйте /next, чтобы создать розыгрыш, или /cancel, чтобы отменить)"

    override val invitations = "Пригласить друзей"

    override val channelSubscription = "Присоединиться к каналу"

    override val youHaveAlreadyAddedInvitations = "Вы уже добавили условие с приглашением друзей!"

    override val enterInvitationsCount = "Введите количество приглашений для участия"

    override val selectLinkedChat = "Выберите подключённый канал (используйте /help, чтобы узнать как привязать канал, или /cancel, чтобы отменить текущий шаг)"

    override val updateChannels = "Обновить привязанные каналы"

    override val channelsUpdated = "Обновлено!"

    override val channelLinkingHelp = bold("Чтобы привязать канал/чат вам нужно выполнить следующие шаги:\n\n") +
            "• Добавить @y9prizebot в ваш канал с " + bold("username") + ", который вы хотите привязать (чтобы любой мог присоединиться к нему), " +
            "позже это будет использовано для проверки участников\n" +
            "• Нажать на кнопку обновить\n\n" +
            "\n" +
            "После добавления бота в канал он автоматически выйдет из него."

    override val channelIsNotLinked = "Этот канал не привязан"

    override val channelIsAlreadyInConditions = "Этот канал уже есть в условиях участия"

    override val giveawayConditions = "Условия участия:"

    override fun subscribeTo(username: String) = regular("Подписаться на ") + bold(username)

    override fun inviteFriends(count: Int) = regular("Пригласить ") + bold("$count") + " ${getValidFriendsForm(count)} в розыгрыш"

    private fun getValidFriendsForm(count: Int) = when {
        count % 10 == 1 -> "друга"
        else -> "друзей"
    }

    override val channelConditionRequiredForInvitations = "Добавьте хотя бы одну подписку на канал, чтобы использовать приглашения"

    override val invitationsCountShouldBePositive = "Количество приглашений должно быть больше нуля"

    override val giveawayInvalid = "Свяжитесь с организаторами, розыгрыш некорретный"

    override val notSubscribedToConditions = "Вы не подписались на все каналы/чаты"

    override val cannotMentionsUser = "Разрешите боту пересылку сообщений, иначе он не сможет вас упомянуть"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Вы пригласили $invitedCount / $requiredCount друзей"

    override val raffleProcessing = "Подождите, идёт обработка розыгрыша"

    override val promoteBot = "Пожалуйста повысьте бота до администратора"

    override val thisChatIsNotPublic = "Сделайте чат публичным, чтобы подключить его"

    override val displayWinnersWithEmoji = "Показывать победителей с эмодзи? Например:\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Это доступно только для розыгрышей с количеством победителей от 2 до 10 включительно"

    override val yes = "Да"

    override val no = "Нет"

}
