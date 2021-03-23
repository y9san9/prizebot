package me.y9san9.prizebot.resources.locales

import dev.inmo.tgbotapi.CommonAbstracts.plus
import dev.inmo.tgbotapi.types.MessageEntity.textsources.bold
import dev.inmo.tgbotapi.types.MessageEntity.textsources.italic
import dev.inmo.tgbotapi.types.MessageEntity.textsources.link
import dev.inmo.tgbotapi.types.MessageEntity.textsources.regular
import me.y9san9.prizebot.extensions.awesomeCut
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

    override val giveawayTitleInput = "Хорошо, давай начнём создание конкурса, для начала отправь название конкурса (Нажми /cancel чтобы отменить)"
    override val giveawayParticipateInput = "Отлично! Теперь отправь текст для кнопки участия (Нажми /cancel чтобы отменить или /skip для использования ${Emoji.HEART} по умолчанию)"

    override val cancel = "Назад"
    override val cancelled = "Отменено!"

    override val skip = "Пропустить"

    override val giveawayCreated = "Розыгрыш создан, ниже можно посмотреть сообщение с демонстрацией"

    override val giveawayParticipateHint = "Чтобы участвовать в розыгрыше, нажми кнопку ниже."

    override val giveawayTitleTooLong = "Максимальная длина для названия розыгрыша - $MAX_TITLE_LEN символов, попробуй ещё раз"

    override val send = "Отправить..."

    override fun participateText(text: String) = "Кнопка участия: $text"

    override val cannotParticipateInSelfGiveaway = "Ты не можете участвовать в своём конкурсе :("

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
}
