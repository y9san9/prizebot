package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.*
import me.y9san9.prizebot.extensions.string.awesomeCut
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

    override val alreadyParticipating = "Ты уже участвуешь в розыгрыше!"

    override val selectGiveawayToView = "Выбери розыгрыш, чтобы посмотреть подробнее"

    override val noGiveawaysYet = "Ты ещё не создал ни одного розыгрыша. Его можно создать с помощью команды /giveaway"

    override val switchPmNoGiveawaysYet = "Нажмите, чтобы создать свой первый розыгрыш"

    override val delete = "Удалить ${Emoji.TRASH}"

    // fixme: remove business logic
    override fun giveawayDeleted(title: String) = regular("Розыгрыш '") +
            bold(title.awesomeCut(maxLength = 30)) + "' удалён"

    override val thisGiveawayDeleted = "Этот розыгрыш удалён"

    override val raffle = "Разыграть ${Emoji.GIFT}"

    override val winner = "Победитель"

    override val deletedUser = "Пользователь удалён"

    override val nobodyIsParticipatingYet = "Никто ещё не участвует в розыгрыше!"

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
            bold("00:00 13.01.2020") + " (используйте /skip, чтобы пропустить, и /cancel, чтобы отменить)"

    override val invalidDateFormat = "Неверный формат даты, попробуйте ещё раз"

    override val selectTimezone = "Выберите часовой пояс с помощью кнопок"

    override val customTimezone = "Другой часовой пояс"

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
}
