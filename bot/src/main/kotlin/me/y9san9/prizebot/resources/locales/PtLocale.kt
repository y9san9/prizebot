package me.y9san9.prizebot.resources.locales

import dev.inmo.micro_utils.language_codes.asIetfLang
import dev.inmo.micro_utils.language_codes.asIetfLanguageCode
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.types.message.textsources.plus
import dev.inmo.tgbotapi.types.message.textsources.regular
import dev.inmo.tgbotapi.utils.buildEntities
import dev.inmo.tgbotapi.utils.newLine
import me.y9san9.extensions.string.awesomeCut
import me.y9san9.prizebot.resources.Emoji
import me.y9san9.prizebot.resources.MAX_TITLE_LEN
import me.y9san9.prizebot.resources.locales.ietf.ignoreDialect

object PtLocale : Locale() {
    override val start = buildEntities {
        +"Olá! Meu nome é " + bold("PrizeBot")
        +", aqui você pode realizar sorteios de prêmios de forma transparente entre usuários aleatórios. "
        +"Um dos principais recursos é que você pode escolher condições para os membros "
        +"(eles devem estar inscritos em alguns canais, etc.). "
        +newLine
        +"O sistema aleatório do Bot é alimentado pelo random.org e "
        +link(text = "aqui", url = "https://github.com/y9san9/prizebot")
        +" você pode conferir o código-fonte."
        +newLine
        +newLine
        +"Para ver todos os comandos disponíveis, use /help"
    }

    override val help =
        "Oi! Eu sou um bot avançado para sorteios, aqui está a lista de comandos disponíveis:\n" +
            "- /start: Me inicia! Você provavelmente já usou este\n" +
            "- /help: Envia esta mensagem\n" +
            "- /giveaway: Cria um novo sorteio\n" +
            "- /my_giveaways: Obtém a lista de sorteios criados\n" +
            "- /language: Seleciona o idioma do bot\n"

    override fun unknownCommand(command: String) =
        "Comando desconhecido '$command'"

    override fun invalidArgsCount(expected: Int, actual: Int) =
        "Eram esperados $expected argumentos para o comando, mas $actual foram encontrados"

    override val enterText = "Digite o texto!"

    override val helpKeyboard = "${Emoji.HELP} Ajuda"
    override val giveawayKeyboard = "${Emoji.GIFT} Novo sorteio"
    override val selfGiveawaysKeyboard = "${Emoji.SETTINGS} Meus sorteios"

    override val giveawayTitleInput = "Ok, vamos começar a criar um novo sorteio, primeiro, envie-me o título (use /cancel para cancelar)"
    override val giveawayParticipateInput = "Legal! Agora envie o texto para o botão de participar (use /cancel para cancelar ou /skip para usar o padrão ${Emoji.HEART})"

    override val cancel = "Cancelar"
    override val cancelled = "Cancelado!"

    override val skip = "Pular"

    override val giveawayCreated = "Seu sorteio foi criado, você pode ver a mensagem de demonstração abaixo"

    override val giveawayParticipateHint = "Para participar do sorteio, pressione o botão abaixo."

    override val giveawayTitleTooLong = "O comprimento máximo permitido para o título é de $MAX_TITLE_LEN caracteres, por favor tente novamente"

    override val send = "Enviando..."

    override fun participateText(text: String) = "Botão de participar: $text"

    override val cannotParticipateInSelfGiveaway = "Você não pode participar do seu próprio sorteio!"

    override val nowParticipating = "Você agora está participando deste sorteio!"

    override val youHaveLeftGiveaway = "Agora você não está mais participando"

    override val alreadyParticipating = "Você já está participando do sorteio"

    override val selectGiveawayToView = "Selecione um sorteio da lista abaixo"

    override val highLoadMessage =
        "O bot está recebendo muitas solicitações no momento, então, por favor, seja paciente. " +
            "Escreva /start para o bot se você ainda não o fez, para que o bot possa notificá-lo mais tarde sobre seu status"

    override val noGiveawaysYet = "Você ainda não criou nenhum sorteio. Crie um com o comando /giveaway"

    override val switchPmNoGiveawaysYet = "Toque para criar seu primeiro sorteio"

    override val delete = "Excluir ${Emoji.TRASH}"

    // FIXME: remove business logic
    override fun giveawayDeleted(title: String) = regular("Sorteio '") +
        bold(title.awesomeCut(maxLength = 30)) + "' excluído"

    override val thisGiveawayDeleted = "Este sorteio foi excluído."

    override val raffle = "Sortear ${Emoji.GIFT}"

    override fun winner(plural: Boolean) =
        if (plural) "Vencedores" else "Vencedor"

    override val deletedUser = "Usuário excluído"

    override fun unknownUser(userId: Long) = "Usuário desconhecido ($userId)"

    override val participantsCountIsNotEnough =
        "Não há participantes suficientes para o sorteio!"

    override val giveawayFinished = "O sorteio já foi finalizado!"

    override val giveawayDoesNotExist = "O sorteio não existe"

    override val selectLocale = "Selecione o idioma do bot com os botões abaixo"

    override val localeSelected = "Idioma selecionado!"

    override fun confirmation(confirmationText: String) =
        "Você tem certeza de que deseja $confirmationText"

    override val confirm = "Confirmar"

    override val deleteGiveawayConfirmation = "excluir sorteio"

    override val raffleGiveawayConfirmation = "realizar sorteio"

    override val enterRaffleDateInput =
        regular("Digite a data do sorteio automático em um destes formatos: ") +
            bold("00:00") + ", " +
            bold("00:00 13.01") + ", " +
            bold("00:00 13.01.2020") +
            " (use /skip para pular ou /cancel para cancelar). " +
            "Você poderá escolher seu fuso horário no próximo passo."

    override val invalidDateFormat = "Formato de data inválido, tente novamente"

    override val selectOffset = "Selecione um fuso horário com os botões abaixo"

    override val customTimeOffset = "Fuso horário personalizado"

    override val `UTC-4` = "Nova York -4"
    override val `UTC-3` = "Brasília -3"
    override val GMT = "GMT +0"
    override val UTC1 = "Berlim +1"
    override val UTC2 = "Kiev +2"
    override val UTC3 = "Moscou +3"
    override val UTC5_30 = "Índia +5:30"
    override val UTC8 = "Pequim +8"
    override val UTC9 = "Tóquio +9"

    override val customTimezoneInput =
        regular("Digite um fuso horário em um destes formatos: ") +
            bold("+9") + ", " + bold("-9:30")

    override val invalidTimezoneFormat = "Formato de fuso horário inválido, tente novamente"

    override val raffleDate = "Data do sorteio"

    override fun lackOfParticipants(giveawayTitle: String) =
        "Não é possível sortear automaticamente o sorteio '$giveawayTitle' porque faltam participantes; você pode sorteá-lo manualmente mais tarde"

    override val winnersCountIsOutOfRange = "Devido ao limite de comprimento de mensagem do Telegram, a quantidade de vencedores deve estar entre 1 e 50"

    override val enterNumber = "Por favor, digite um número"

    override val enterWinnersCount = "Digite a quantidade de vencedores para este sorteio (use /skip para definir o valor padrão (1) ou /cancel para cancelar)"

    override val winnersCount = "Quantidade de vencedores"

    override val chooseConditions = "Agora você pode escolher as condições para os membros do sorteio (use /next para criar o sorteio sem condições ou /cancel para cancelar)"

    override val chooseMoreConditions = "Escolha a próxima condição (use /next para criar o sorteio ou /cancel para cancelar)"

    override val invitations = "Convites de Amigos"

    override val channelSubscription = "Entrar no canal"

    override val youHaveAlreadyAddedInvitations = "Você já adicionou a condição de convites!"

    override val enterInvitationsCount = "Digite a quantidade de convites necessários para participar"

    override val selectLinkedChat = "Selecione o chat vinculado com os botões abaixo (use /help para saber como vincular um canal, ou use /cancel para cancelar este passo)"

    override val updateChannels = "Atualizar canais vinculados"

    override val channelsUpdated = "Canais vinculados atualizados!"

    override val channelLinkingHelp =
        bold("Para vincular um canal, você deve seguir estas etapas:\n\n") +
            "• Adicione @y9prizebot como administrador ao canal/chat que você deseja vincular (" +
            bold("ele deve ter um nome de usuário") +
            " para que qualquer pessoa possa entrar), " +
            "posteriormente ele será usado para verificar se o membro entrou\n" +
            "• Depois, basta clicar no botão de atualizar e selecionar o canal\n\n" +
            bold("Caso o bot já esteja no chat:\n\n") +
            "\n" +
            "Se o bot já estiver no canal, mas você não vir o canal na lista, remova e adicione o bot novamente."

    override val channelIsNotLinked = "Este canal não está vinculado!"

    override val channelIsAlreadyInConditions = "Este canal já é uma condição"

    override val giveawayConditions = "Condições do sorteio:"

    override fun subscribeTo(username: String) =
        regular("Inscreva-se em ") + bold(username)

    override fun inviteFriends(count: Int) =
        regular("Convide ") + bold("$count") + " amigo" +
            (if (count > 1) "s" else "") + " para o sorteio"

    override val channelConditionRequiredForInvitations =
        "Pelo menos uma inscrição em canal é necessária se você quiser adicionar " +
            "Convites de amigos"

    override val invitationsCountShouldBePositive = "A quantidade de convites deve ser um número positivo"

    override val giveawayInvalid = "Contate o organizador porque o sorteio parece ser inválido"

    override val notSubscribedToConditions = "Você não entrou em todos os chats/canais"

    override val cannotMentionsUser = "Por favor, permita que o bot 'Encaminhe Mensagens' nas configurações, caso contrário o bot não poderá mencionar você (A configuração será aplicada em até 5 minutos)"

    override fun friendsAreNotInvited(invitedCount: Int, requiredCount: Int) =
        "Você convidou $invitedCount / $requiredCount amigos"

    override val raffleProcessing = "Por favor, aguarde, o sorteio está sendo processado"

    override val promoteBot = "Promova o bot a administrador primeiro"

    override val thisChatIsNotPublic = "Este chat não é público, por favor adicione um nome de usuário"

    override val displayWinnersWithEmoji =
        "Exibir os vencedores com emojis assim?\n\n" +
            "${Emoji.FIRST_PLACE} Foo Bar\n" +
            "${Emoji.SECOND_PLACE} Bar Foo\n" +
            "${Emoji.THIRD_PLACE} Baz Baz\n\n" +
            "Isso está disponível apenas para sorteios com quantidades de vencedores de 2 a 10"

    override val yes = "Sim"

    override val no = "Não"
}
