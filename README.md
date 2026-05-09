# Planning Poker

Projeto de Planning Poker desenvolvido em Java com foco em orientação a objetos, modelagem de domínio e separação de responsabilidades.

O sistema simula uma sessão de Planning Poker via terminal (CLI), permitindo criação de sessões, adição de participantes, votação com cartas Fibonacci e múltiplas rodadas até atingir consenso.

---

# Objetivos do Projeto

* Praticar modelagem orientada a objetos
* Aplicar encapsulamento e imutabilidade
* Trabalhar com enums, collections e regras de negócio
* Implementar fluxo de votação baseado em consenso
* Simular uma arquitetura backend simples em Java
* Criar uma base sólida antes de avançar para Spring Boot e SQL

---

# Tecnologias Utilizadas

* Java
* IntelliJ IDEA
* Collections Framework
* Programação Orientada a Objetos (POO)

---

# Estrutura do Projeto

```text
src/
└── planningpoker/
    ├── Main.java
    │
    ├── app/
    │   └── PlanningPokerApp.java
    │
    ├── service/
    │   └── PlanningPokerService.java
    │
    └── domain/
        ├── Participant.java
        ├── PlanningCard.java
        ├── PlanningSession.java
        ├── Vote.java
        ├── VotingResult.java
        └── VotingStatus.java
```

---

# Arquitetura

O projeto foi dividido em camadas simples para manter separação de responsabilidades.

## app

Responsável pela interação com o usuário via terminal.

Exemplo:

* leitura de dados
* exibição de resultados
* controle do fluxo principal da aplicação

---

## service

Responsável por orquestrar o fluxo da aplicação.

Centraliza operações como:

* criação de sessão
* adição de participantes
* registro de votos
* reinício de rodadas
* geração de resultados

---

## domain

Contém as entidades e regras de negócio do sistema.

Exemplo:

* participantes
* votos
* sessão de planning poker
* cálculo de resultados

---

# Fluxo da Aplicação

```text
Cria sessão
→ Adiciona participantes
→ Exibe cartas disponíveis
→ Coleta votos
→ Revela votos
→ Calcula resultado
→ Verifica consenso
→ Reinicia rodada (caso necessário)
→ Finaliza sessão
```

---

# Classes Principais

## PlanningCard

Enum responsável pelas cartas do Planning Poker.

Cartas disponíveis:

```text
0 ½ 1 2 3 5 8 13 20 40 100 ? ∞ ☕
```

Responsabilidades:

* representar cartas válidas
* impedir criação de cartas inexistentes
* converter entrada do usuário em enum
* diferenciar cartas numéricas de especiais

---

## Participant

Representa um participante da sessão.

Possui:

* id único
* nome de exibição

Responsabilidades:

* identidade do participante
* comparação via equals/hashCode
* encapsulamento dos dados

---

## Vote

Representa um voto individual.

Conecta:

* Participant
* PlanningCard

Responsabilidades:

* armazenar o voto atual do participante
* validar dados recebidos
* identificar votos numéricos

---

## PlanningSession

Centraliza o controle da rodada.

Responsabilidades:

* gerenciar participantes
* controlar votos
* controlar estado da rodada
* impedir votos após reveal
* verificar se todos votaram
* reiniciar rodadas

---

## VotingResult

Responsável pelos cálculos da rodada.

Responsabilidades:

* calcular média
* identificar maior e menor voto
* verificar consenso
* detectar cartas especiais
* gerar status da rodada

---

# Regras de Negócio

* Um participante só pode votar se estiver na sessão
* Cada participante possui apenas um voto por rodada
* O último voto sobrescreve o anterior
* Não é permitido votar após reveal
* A rodada só pode ser revelada quando todos votarem
* Rodadas reiniciam automaticamente quando não há consenso
* O sistema finaliza apenas quando existe consenso

---

# Exemplo de Execução

```text
=== Planning Poker ===
Session name: Backend API

Add participants
Type an empty name to finish.
Participant name: Lucas
Participant name: Mariana
Participant name:

Available cards:
0 ½ 1 2 3 5 8 13 20 40 100 ? ∞ ☕

Voting started
Lucas, choose a card: 5
Mariana, choose a card: 13

=== Votes ===
Lucas voted 5
Mariana voted 13

=== Result ===
Total votes: 2
Numeric votes: 2
Average: 9.0
Consensus: false
Status: NEEDS_DISCUSSION

No consensus reached.
Starting a new round...
```

---

# Conceitos Aplicados

* Encapsulamento
* Imutabilidade
* Collections
* Enum
* equals/hashCode
* Separação de responsabilidades
* Modelagem de domínio
* Arquitetura em camadas
* Fluxo baseado em estado

---

# Possíveis Melhorias Futuras

* API REST com Spring Boot
* Persistência com SQL
* Histórico de rodadas
* Interface web
* WebSocket para votação em tempo real
* Dockerização
* Testes unitários completos
* Autenticação de usuários

---

# Aprendizados

Este projeto foi desenvolvido com foco em fortalecer fundamentos de backend e orientação a objetos antes da utilização de frameworks.

A ideia principal foi primeiro modelar corretamente o domínio da aplicação em Java puro, garantindo entendimento das regras de negócio e responsabilidades das classes.

---

# Como Executar

## Pré-requisitos

* Java JDK 17+ instalado
* IntelliJ IDEA (opcional)
* Git instalado

---

## Clone o repositório

```bash
git clone <repository-url>
```

---

## Entre na pasta do projeto

```bash
cd Planning-Poker
```

---

## Compilando via terminal

No Linux/Mac:

```bash
javac -d out $(find src -name "*.java")
```

No Windows (PowerShell):

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

---

## Executando o projeto

Linux/Mac:

```bash
java -cp out planningpoker.Main
```

Windows:

```powershell
java -cp out planningpoker.Main
```

---

## Executando pelo IntelliJ IDEA

1. Abra o projeto no IntelliJ
2. Aguarde indexação e configuração do SDK
3. Abra o arquivo:

```text
src/planningpoker/Main.java
```

4. Clique no botão ▶ ao lado do método main
5. O terminal da aplicação será iniciado automaticamente

---

# Autor

Lucas Santos Cunha

https://www.linkedin.com/in/lucas-santos-cunha-00673734a/
