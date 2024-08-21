package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN

object UkLocale : Locale() {
    override val start = regular("Привіт! Мене звати ") + bold("PrizeBot") +
            regular(
                ", за допомогою мене можна розігравати призи. " +
                        "Головна особливість бота - ви можете обирати умови для учасників " +
                        "(підписка на канал і тд.). Випадковість досягається за допомогою random.org, а "
            ) +
            link(text = "тут", url = "https://github.com/y9san9/prizebot") + " можна подивитись початковий код.\n" +
            "Щоб подивитись список всіх доступних команд, введіть /help"

    override fun unknownCommand(command: String) = "Невідома команда '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Очікується $expected аргументів для команди, але знайдено $actual"

    override val enterText = "Введіть текст!"

    override val help = "Привіт! Я покращений бот для розіграшів, ось список доступних команд:\n" +
            "- /help: Відправити це повідомлення\n" +
            "- /giveaway: Створити новий розіграш\n" +
            "- /my_giveaways: Переглянути створені розіграші\n" +
            "- /language: Вибрати мову бота\n"

    override val helpKeyboard = "${Emoji.HELP} Допомога"
    override val giveawayKeyboard = "${Emoji.GIFT} Новий розіграш"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Мої розіграші"

    override val giveawayTitleInput =
        "Добре, розпочнім створення розіграшу, для початку відправ назву конкурсу (Натисни /cancel щоб скасувати)"
    override val giveawayParticipateInput =
        "Чудово! Тепер відправ текст для кнопки участі (Натисни /cancel щоб скасувати або /skip для використання ${Emoji.HEART} за замовчуванням.)"

    override val cancel = "Назад"
    override val cancelled = "Скасовано!"

    override val skip = "Пропустити"

    override val giveawayCreated = "Розіграш створено, нижче можна подивитись повідомлення з демонстрацією"

    override val giveawayParticipateHint = "Щоб взяти участь у розіграші, натисни кнопку нижче."

    override val giveawayTitleTooLong =
        "Максимальна довжина для назви розіграшу - $MAX_TITLE_LEN символів, спробуй ще раз"

    override val send = "Відправити..."

    override fun participateText(text: String) = "Кнопка участі: $text"

    override val cannotParticipateInSelfGiveaway = "Ви не можете брати участь у власному конкурсі :("

    override val nowParticipating = "Тепер ти береш участь у розіграші!"

    override val youHaveLeftGiveaway = "Ви вийшли з розіграшу."

    override val alreadyParticipating = "Ти береш участь у розіграші"

    override val highLoadMessage =
        "Зараз бот знаходиться під сильним навантаженням, будь ласка, прояви трішки терпіння. " +
                "Якщо ти ще не писав боту, напиши /start, бот пізніше повідомить про свій статус."

    override val selectGiveawayToView = "Обери розіграш, щоб подивитись детальніше."

    override val noGiveawaysYet = "Ти ще не створив жодного розіграшу. Щоб створити, використовуй команду /giveaway"

    override val switchPmNoGiveawaysYet = "Натисни, щоб створити свій перший розіграш"

    override val delete = "Видалити ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Розіграш '") +
            bold(title.awesomeCut(maxLength = 30)) + "' видалено"

    override val thisGiveawayDeleted = "Цей розіграш видалено"

    override val raffle = "Розіграти ${Emoji.GIFT}"

    override fun winner(plural: Boolean) = if (plural) "Переможці" else "Переможець"

    override val deletedUser = "Користувача видалено"

    override fun unknownUser(userId: Long) = "Невідомий користувач ($userId)"

    override val participantsCountIsNotEnough =
        "Участників недостатньо, щоб обрати переможця!"

    override val giveawayFinished = "Розіграш вже завершено!"

    override val giveawayDoesNotExist = "Такого розіграшу не існує."

    override val selectLocale = "Вибери мову бота за допомогою кнопок нижче"

    override val localeSelected = "Мову змінено"

    override fun confirmation(confirmationText: String) = "Ви впевнені, що хочете $confirmationText?"

    override val confirm = "підтвердити"

    override val deleteGiveawayConfirmation = "видалити розіграш"

    override val raffleGiveawayConfirmation = "розіграти приз"

    override val enterRaffleDateInput = regular("Введіть дату для автоматичного розіграшу в одному з форматів: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") + " (використовуйте /skip, щоб пропустити, або /cancel, щоб скасувати). " +
            "Зміщення часу можна буде вибрати на наступному етапі."

    override val invalidDateFormat = "Неправильний формат дати, спробуй ще раз."

    override val selectOffset = "Виберіть зміщення часу за допомогою кнопок"

    override val customTimeOffset = "Інше зміщення"

    override val `UTC-4` = "Нью-Йорк -4"
    override val GMT = "Грінвіч +0"
    override val UTC1 = "Берлін +1"
    override val UTC2 = "Київ +2"
    override val UTC3 = "Москва +3"
    override val UTC5_30 = "Індія +5:30"
    override val UTC8 = "Пекін +8"
    override val UTC9 = "Токіо +9"

    override val customTimezoneInput = regular("Введіть часовий пояс в одному з форматів: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Неправильний формат часового поясу, спробуйте ще раз"

    override val raffleDate = "Дата розіграшу"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Недостатньо учасників, щоб автоматично розіграти '$giveawayTitle', ви можете зробити це власноруч пізніше"

    override val winnersCountIsOutOfRange =
        "Кількість переможців має бути від 1 до 50 000"

    override val winnersCount = "Кількість переможців"

    override val enterNumber = "Будь ласка, введіть число"

    override val enterWinnersCount = "Введіть кількість переможців (використовуйте /skip, щоб встановити значення за замовчуванням (1), або /cancel, щоб скасувати)"

    override val chooseConditions = "Тепер виберіть умови розіграшу (використовуйте /next, щоб зробити без умов, або /cancel, щоб скасувати)"

    override val chooseMoreConditions = "Виберіть наступну умову (використовуйте /next, щоб створити розіграш, або /cancel, щоб скасувати)"

    override val invitations = "Запросити друзів"

    override val channelSubscription = "Приєднатися до каналу"

    override val youHaveAlreadyAddedInvitations = "Ви вже добавили умову з запрошенням друзів!"

    override val enterInvitationsCount = "Введіть кількість запрошень для участі"

    override val selectLinkedChat = "Виберіть підключений канал (використовуйте /help, щоб дізнатися, як привязати канал, або /cancel, щоб скасувати поточний крок)"

    override val updateChannels = "Оновити прив'язанні канали"

    override val channelsUpdated = "Оновлено!"

    override val channelLinkingHelp = bold("Щоб прив'язати канал/чат, вам потрібно виконати наступні кроки:\n\n") +
            "• Додати @y9prizebot до вашого каналу з " + bold("username") + ", який ви хочете прив'язати (щоб хто завгодно міг приєднатися до нього), " +
            "пізніше це буде використано для перевірки учасників\n" +
            "• Натиснути на кнопку оновлення\n\n" +
            "\n" +
            "Якщо бот вже в каналі, але ви не бачите канал в списку, видаліть і додайте бота знову."

    override val channelIsNotLinked = "Цей канал не прив'язано"

    override val channelIsAlreadyInConditions = "Цей канал вже є в умовах участі"

    override val giveawayConditions = "Умови участі:"

    override fun subscribeTo(username: String) = regular("Підписатись на ") + bold(username)

    override fun inviteFriends(count: Int) = regular("Запросити ") + bold("$count") + " ${getValidFriendsForm(count)} до розіграшу"

    private fun getValidFriendsForm(count: Int) = when {
        count % 10 == 1 && count != 11 -> "друга"
        else -> "друзів"
    }

    override val channelConditionRequiredForInvitations = "Додайте принаймні одну підписку на канал, щоб використовувати запрошення"

    override val invitationsCountShouldBePositive = "Кількість запрошень має бути більше нуля"

    override val giveawayInvalid = "Зв'яжіться з організаторами, розіграш некоректний"

    override val notSubscribedToConditions = "Ви не підписались на всі канали/чати"

    override val cannotMentionsUser = "Дозвольте 'Пересилати повідомлення' в налаштуваннях, бо інакше бот не зможе вас згадати (налаштування вступить в силу через 5 хвилин)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) = "Ви запросили $invitedCount / $requiredCount друзів"

    override val raffleProcessing = "Почекайте, триває обробка розіграшу"

    override val promoteBot = "Будь ласка підвищіть бота до адміністратора"

    override val thisChatIsNotPublic = "Зробіть чат публічним, щоб підключити його"

    override val displayWinnersWithEmoji = "Показувати переможців з емодзі? Наприклад:\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Це доступно тільки для розіграшів з кількістю переможців від 2 до 10 включно"

    override val yes = "Так"

    override val no = "Ні"
}
