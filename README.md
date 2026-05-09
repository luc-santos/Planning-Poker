⚠️ **Projeto em construção (WIP - Work in Progress)** 

Projeto Planning-Poker

<img width="1024" height="1536" alt="image" src="https://github.com/user-attachments/assets/1b2e01a3-0a0b-443e-90f3-150659c9874a" />

Planning Card

Tipo de classe enum, é usado pelos valores serem fixos e conhecidos, evita erros (criar carta inexistente), facilita comparação.

Valores

ex - FIVE(“5”, 5.0) (String, Double)

os valores recebidos são privados e finais, por questões de segurança, só podem ser acessado via getters e final para esse valor não poder ser alterado de forma alguma após o voto.

necessário uso de Double e não double, por conta dos valores ?, ☕ e ∞.

Métodos

getters de Label e Value, getLabel(), getValue(), apenas retornos de exibição.

isNumeric(), essencial nesse caso, por termos valores null, colabora com a classe VotingResult

fromLabel(String label)

converte entrada em carta, método estático pra poder ser utilzado sem objeto

checa se o o valor recebido (label) é null, no caso de question, infinity e coffee, e retorna null se for

percorre os valores possiveis em loop, e caso bata com um valor não null, retorna card, variável criada no inicio do loop, com o valor equivalente.

ex - “5” → PlanningCard.FIVE

@Override - transformar o valor retornado em string, e não em HashValue, já que será tratado como tal dentro da VM java.

# Participant

O objetivo dessa classe é classificar os participantes, recebendo duas informações:
id e name

Ambos são variáveis privadas e finais, novamente por questões de segurança

id = “Valor” real do participante, será usado em comparações (equals(), hashCode())

name = apenas para fins de exibição, não define identidade, pois dois participantes podem ter o mesmo nome, mas nunca o mesmo id.

No construtor, tratamos os dados, checa se id e name são variáveis que não apontam pra nenhum objeto, e/ou é uma string vazia, se sim, usamos throw new, que categoriza o mesmo como um erro para o terminal, jogando uma mensagem de argumento inválido.

Métodos

Getters de id e name

@Override 1 - toString em name, transformar valor retornado em string, e não em hashValue.

@Override 2 - checa o objeto, via id, em caso de multiplas sessões, dados vindo de fora ou votação em tempo real. Em suma, checa se 2 participantes são iguais via id.

@Override 3 - gera um valor em inteiro para o objeto, baseado no id, usado principalmente no equals, ele compara os valores em hashCode, e não a string id.

# Vote

A classe Vote se conecta tanto com as classes Participant como com a classe PlanningCard, participant representa quem vota, PlanningCard o que foi votado e Vote conecta os 2, exemplo:

Participant - id = 1, name = Mariana → Vote - Define o que foi votado, exemplo: 5 -> esse valor é comparado com a carta PlanningCard, e se torna:
Vote {

id: 1
name: Mariana
card: 5

}

Define as variáveis vindas das Classes conectadas como privadas e finais, novamente por questões de segurança e imutabilidade, dado o fato que o voto de um participante não deve mudar na mesma sessão.

O construtor de Vote recebe valores das Classes Participant e PlanningCard, valida esses valores e cria as variáveis, em caso de erro, gera uma mensagem de erro que será exibida no terminal.

Métodos

Getters de participant e card, recebidos das Classes colaboradoras

isNumericVote() - checa se é um voto numérico ou um dos 3 valores especiais.

@Override1 - Transforma os valores de participant e card em string, e adiciona “ voted “ no meio, para questões de exibição, exemplo:

Mariana voted 5;

@Override2 - Define que se, em caso de dois votos em uma mesma sessão, por qualquer que seja o motivo, o voto válido é o voto final para aquela sessão em especifico.

@Override3 - Transforma o participant em hashValue, o que é essencial para o map da classe PlanningSession.

# PlanningSession

A classe PlanningSession centraliza o controle da votação, gerenciando participantes, votos e o estado da rodada, garantindo as regras do fluxo de Planning Poker. A mesma possui uma série de responsabilidades, que estão representadas no diagrama acima.

Fluxo da sessão - Adiciona participantes →Inicia uma rodada →Recebe os votos →Verifica se todos os participantes votaram →Calcula os resultados → Reinicia rodada.

A classe recebe duas informações principais:

id e name

Ambos são privados e finais, seguindo o padrão das demais classes, garantindo imutabilidade e segurança dos dados após a criação da sessão.

id = identificador único da sessão, utilizado para diferenciar sessões distintas.

name = nome da sessão, utilizado apenas para exibição.

Além disso, a classe possui duas estruturas principais:

participants = Map<String, Participant>

Armazena os participantes da sessão, utilizando o id como chave, garantindo unicidade e acesso rápido.

votes = Map<Participant, Vote>

Armazena os votos da rodada atual, utilizando o Participant como chave, garantindo que cada participante tenha apenas um voto por rodada.

Também possui:

revealed = boolean

Define o estado da rodada, indicando se os votos já foram revelados ou não.

Regras de Negócio

- Um participante só pode votar se estiver na sessão
- Um participante só pode ter um voto por rodada (último voto sobrescreve o anterior)
- Não é permitido votar após os votos serem revelados
- Os votos só podem ser revelados quando todos os participantes tiverem votado
- A rodada pode ser reiniciada após o reveal

Métodos

addParticipant(Participant participant)

Adiciona um participante à sessão.

Valida se o participante não é null.

Utiliza o id como chave no Map, garantindo que não existam duplicações.

removeParticipant(String participantId)

Remove um participante da sessão.

Valida se o id é válido.

Remove o participante do Map de participantes e, caso exista, remove também seu voto da rodada atual.

vote(String participantId, PlanningCard card)

Registra o voto de um participante.

Valida se a rodada ainda não foi revelada.

Valida se o participante existe na sessão.

Cria um novo Vote e armazena no Map de votos.

Caso o participante já tenha votado, o novo voto sobrescreve o anterior, garantindo que o voto final seja sempre o mais recente.

hasParticipant(String participantId)

Verifica se um participante está presente na sessão.

Retorna true ou false com base na existência no Map.

hasVoted(String participantId)

Verifica se um participante já votou na rodada atual.

Caso o participante não exista, retorna false.

Caso exista, verifica se há um voto associado a ele.

allParticipantsVoted()

Verifica se todos os participantes votaram.

Retorna true apenas se:

- existir pelo menos um participante
- o número de votos for igual ao número de participantes

revealVotes()

Define o fim da rodada.

Valida se todos os participantes votaram.

Caso positivo, altera o estado revealed para true.

A partir desse momento, nenhum novo voto pode ser registrado.

resetVotes()

Reinicia a rodada.

Remove todos os votos atuais e redefine o estado revealed para false, permitindo uma nova rodada de votação.

getParticipants()

Retorna a coleção de participantes da sessão.

getVotes()

Retorna a coleção de votos da rodada atual.

isRevealed()

Retorna o estado atual da rodada (se os votos já foram revelados).

getId() / getName()

Getters para identificação e exibição da sessão.

Observações

A classe PlanningSession não define um fim definitivo para a sessão, permitindo múltiplas rodadas de votação (loop contínuo).

O modelo atual representa uma sala de Planning Poker reutilizável, onde cada rodada corresponde a uma nova estimativa.

O controle de fluxo é baseado no estado revealed, que funciona como uma forma simplificada de gerenciamento de estado da rodada.
