# Wunderwelt: Battleworld

> RPG textual mobile ambientado no Battleworld da Marvel, desenvolvido em Java para Android com arquitetura MVVM.

Estado executável e limitações: [MVP implementado](docs/MVP.md). Convenções visuais:
[assets](docs/assets/README.md). Referências de canon: [fontes](docs/CANON.md).
As seções conceituais abaixo registram a visão do projeto; o escopo efetivamente entregue está em `docs/MVP.md`.

## 📖 Sobre o projeto

**Wunderwelt: Battleworld** é um RPG textual single-player com combates por turnos, desenvolvido como projeto mobile em **Java**, utilizando **Android Studio**, **Fragments** e arquitetura **MVVM**.

A proposta combina uma experiência narrativa ambientada no **Mundo Bélico (Battleworld)** com o consumo da **Comic Vine API**, responsável por fornecer dados relacionados ao universo dos quadrinhos.

O projeto prioriza uma experiência simples: criação de personagem, progressão narrativa, exploração de regiões e batalhas por turnos, sem sistemas excessivamente complexos de RPG.

---

## 🌌 Premissa

Após obter o poder dos **Beyonders**, Victor von Doom reconstrói os fragmentos da realidade e cria o **Battleworld**, um mundo formado por diferentes domínios e realidades.

Doom passa a governar esse novo mundo como o **Deus Imperador Destino**.

O jogador assume o papel de um novo personagem vivendo nesse mundo e inicia uma jornada através dos diferentes domínios de Battleworld.

O objetivo final da campanha é chegar até **Doomstadt** e enfrentar o próprio Doom.

---

## 🎮 Tipo de RPG

Wunderwelt será um:

**RPG textual single-player com combate por turnos.**

A experiência será focada principalmente em:

- narrativa;
- escolhas simples;
- criação de personagem;
- exploração de regiões;
- atributos;
- batalhas por turnos;
- progressão da história.

A apresentação e o ritmo das batalhas possuem inspiração em RPGs clássicos como **EarthBound**, priorizando textos, menus e combates simples em vez de exploração tridimensional ou sistemas complexos.

---

## 🎬 Fluxo geral

```text
INTRODUÇÃO
    ↓
CRIAÇÃO DO PERSONAGEM
    ↓
REGIÃO DE ORIGEM
    ↓
INÍCIO DA HISTÓRIA
    ↓
EXPLORAÇÃO DOS DOMÍNIOS
    ↓
EVENTOS E COMBATES
    ↓
DOOMSTADT
    ↓
DEUS IMPERADOR DESTINO
```

---

## 🎥 Introdução

Antes da criação do personagem, o jogador verá uma introdução narrativa curta e cinematográfica.

Ela apresentará:

- o fim da realidade anterior;
- os Beyonders;
- Victor von Doom obtendo seu poder;
- a criação do Battleworld;
- Doom se tornando o Deus Imperador Destino;
- a existência dos diferentes domínios.

A intenção é contextualizar o jogador antes de apresentar qualquer menu ou mecânica.

---

# 🧑 Criação de personagem

O jogador controla **um único protagonista** durante toda a aventura.

A criação será dividida em etapas:

1. Raça;
2. Variante racial, quando aplicável;
3. Classe;
4. Arma;
5. Região de origem;
6. Distribuição de atributos;
7. Resumo do personagem.

---

## 🧬 Raças

### Humano

Variantes:

- Normal
- Super Soldado
- Radiação Gama

### Mutante

Personagem com habilidades de origem mutante.

O poder é sorteado uma única vez ao selecionar Mutante e permanece até reiniciar a criação.
Não existe uma raça separada chamada Poder Aleatório, nem reroll nesta versão.

### Asgardiano

Variantes:

- Aesir
- Jotun

### Simbionte

- Simbionte sem hospedeiro

---

# ⚔️ Classes e armas

## Guerreiro

Focado em combate físico e armas brancas.

Armas:

- Espada padrão
- Espada grande
- Adaga

Atributos relacionados:

- Força
- Vitalidade

---

## Brawler

Especialista em combate corpo a corpo.

Estilos:

- Artes marciais
- Força bruta
- Boxing

Atributos relacionados:

- Força
- Agilidade

---

## Marksman

Especialista em ataques à distância.

Armas:

- Arco e flecha
- Pistola
- Rifle

Atributos relacionados:

- Agilidade
- Inteligência

---

## Mago

Focado em habilidades místicas.

Especializações:

- Artes místicas
- Arte do caos
- Necromancia

Atributos relacionados:

- Energia
- Inteligência

---

## Tecnólogo

Utiliza tecnologia como principal forma de combate.

Armas:

- Drone
- Luva elétrica
- Espada elétrica

Atributos relacionados:

- Inteligência
- Energia

### Drone

O drone é tratado pelo sistema como uma **arma**, e não como um companheiro.

Não haverá sistema de party ou equipe controlável.

---

# 📊 Atributos

Todos os atributos começam em **0**.

Durante a criação, o jogador recebe **10 pontos** para distribuir.

## Força

Relacionada principalmente a:

- dano físico;
- golpes corpo a corpo;
- armas pesadas.

## Vitalidade

Relacionada principalmente a:

- vida;
- resistência.

## Agilidade

Relacionada principalmente a:

- velocidade;
- iniciativa;
- chance de crítico.

## Inteligência

Relacionada principalmente a:

- tecnologia;
- equipamentos;
- determinadas habilidades técnicas e místicas.

## Energia

Relacionada principalmente a:

- poderes;
- ataques especiais;
- habilidades místicas.

---

# 🌍 Regiões

## Vale do Destino — Valley of Doom

Domínio inspirado no Velho Oeste.

Características:

- cidades pequenas;
- confrontos armados;
- estética western;
- baixa tecnologia.

---

## King James' England

Domínio com estética medieval e renascentista.

Características:

- baixa tecnologia;
- armas tradicionais;
- sociedades antigas;
- versões alternativas de elementos Marvel.

---

## K'un-Lun

Região ligada ao misticismo e às artes marciais.

Características:

- monastérios;
- treinamento;
- mestres marciais;
- poderes místicos.

---

## Killville

Região densamente ocupada e dominada pelo crime.

Características:

- construções verticais;
- becos;
- organizações criminosas;
- ambientes urbanos perigosos.

---

## Nueva York 2099

Domínio baseado no universo Marvel 2099.

Características:

- cyberpunk;
- megacorporações;
- tecnologia avançada;
- grandes construções;
- desigualdade social.

---

## Tecnópolis — Technopolis

Um dos domínios tecnologicamente mais avançados de Battleworld.

Características:

- tecnologia avançada;
- máquinas e armaduras;
- controle severo;
- quarentena.

---

# 🗺️ Estrutura da história

A campanha será **majoritariamente linear**, com pequenas variações baseadas nas escolhas feitas durante a criação do personagem.

```text
REGIÃO DE ORIGEM
       ↓
PRIMEIRO ARCO
       ↓
NOVOS DOMÍNIOS
       ↓
CONFLITOS REGIONAIS
       ↓
APROXIMAÇÃO DE DOOMSTADT
       ↓
DOOMSTADT
       ↓
DEUS IMPERADOR DESTINO
```

Raça e região de origem poderão alterar:

- diálogos;
- introduções;
- eventos;
- determinadas interações;
- alguns inimigos.

A história principal, porém, será compartilhada entre todos os personagens para manter o escopo do projeto controlável.

---

# 💥 Sistema de batalha

Os combates serão realizados por **turnos**.

Estrutura planejada:

```text
TURNO DO JOGADOR
      ↓
ESCOLHA DA AÇÃO
      ↓
CÁLCULO DO RESULTADO
      ↓
TURNO DO INIMIGO
      ↓
PRÓXIMO TURNO
```

As ações poderão incluir opções como:

- Ataque;
- Habilidade;
- Defesa.

A batalha termina quando a vida do jogador ou do inimigo chega a zero.

---

## 🔥 Tipos de combate

O sistema utilizará tipos simples de combate:

- Marcial
- Balístico
- Tecnológico
- Místico

A proposta inicial é criar relações de vantagem e desvantagem entre esses tipos.

Exemplo:

```text
Marcial → Balístico
Balístico → Tecnológico
Tecnológico → Marcial
Místico → Tecnológico
```

Ataques com vantagem causam mais dano.

Ataques em desvantagem causam menos dano.

Demais relações causam dano normal.

A matriz final poderá ser ajustada durante os testes de balanceamento.

---

## 💢 Acertos críticos

A chance de crítico será relacionada principalmente à **Agilidade**.

Um ataque crítico causará dano adicional.

O multiplicador final será definido durante o balanceamento.

---

# 👤 Sistema de grupo

Wunderwelt será focado em **um único personagem controlável**.

Não haverá:

- party;
- recrutamento;
- troca entre personagens;
- gerenciamento de companheiros.

O Drone do Tecnólogo continua sendo considerado uma arma.

---

# 🌐 Comic Vine API

O projeto utiliza a **Comic Vine API** como fonte externa de dados relacionados aos quadrinhos.

A integração poderá utilizar informações como:

- personagens;
- poderes;
- locais;
- edições;
- outros dados relacionados ao universo Marvel.

A comunicação seguirá a separação definida pela arquitetura:

```text
Fragment
   ↓
ViewModel
   ↓
Repository
   ↓
ComicVineApi
   ↓
Comic Vine API
```

A chave da API não deve ser adicionada diretamente ao código ou enviada ao repositório Git.

---

# 🏗️ Tecnologias

- Java
- Android Studio
- Android Views
- Empty Views Activity
- Fragments
- MVVM
- Android Navigation
- Comic Vine API
- Git / GitHub

---

# 🧱 Arquitetura

A aplicação parte de uma única `MainActivity`, utilizada como container para os Fragments.

```text
MainActivity
     ↓
NavHostFragment
     ↓
nav_graph.xml
     ↓
Fragments
```

A organização principal segue:

```text
Fragment
   ↓
ViewModel
   ↓
Model / Repository
```

---

## 📁 Estrutura principal

```text
com.seuprojeto.marvelbattleworld/
│
├── MainActivity.java
│
├── data/
│   ├── api/
│   ├── model/
│   └── repository/
│
├── model/
│   └── game/
│
├── ui/
│   ├── intro/
│   ├── character/
│   ├── story/
│   ├── battle/
│   └── marvel/
│
└── utils/
```

---

## 🧑‍🚀 Criação do personagem no MVVM

Os Fragments responsáveis pela criação compartilham um único `CharacterViewModel`.

```text
RaceFragment ──────────┐
ClassFragment ─────────┤
RegionFragment ────────┼──► CharacterViewModel
AttributesFragment ────┤
CharacterSummary ──────┘
```

O ViewModel mantém o estado da criação do personagem enquanto o jogador navega entre as etapas.

---

# 🎯 Objetivo da campanha

A progressão principal leva o jogador através dos domínios de Battleworld até:

# Doomstadt

onde acontece o confronto contra:

# Deus Imperador Destino

---

# 🚧 Status atual

O projeto possui um vertical slice funcional:

- splash nativo, abertura animada pulável e menu;
- criação de personagem com raça, variante, classe, arma, região e dez pontos de atributos;
- ficha visual validada, reaberta durante a jornada;
- cena narrativa variável por região, duas escolhas e consequências;
- batalha por turnos com ataque, habilidade, defesa, crítico e vantagens de tipo;
- inimigo específico por região, vitória, derrota e repetição da batalha;
- quatro encontros por partida: origem, passagem regional, Manhattan e Doomstadt;
- dois desfechos e opção de reiniciar;
- catálogo comparativo de seis pares normal/Battleworld, busca e detalhes com Comic Vine opcional;
- estados de loading, resultado vazio e personagem não encontrado;
- tela de créditos e placeholders construídos com recursos XML.

Os valores de dano, HP, crítico e vantagem são provisórios e estão centralizados em
`Constants` e `DamageCalculator` para facilitar o balanceamento posterior.

## ▶️ Executando o projeto

Crie ou atualize o arquivo local `local.properties` sem versioná-lo:

```properties
sdk.dir=CAMINHO_DO_ANDROID_SDK
# Opcional: obter sua própria chave da Comic Vine. Nunca commitar o valor.
COMIC_VINE_API=
```

Então execute:

```text
gradlew.bat testDebugUnitTest lintDebug assembleDebug
```

Toda a campanha e os seis pares funcionam sem internet. Com chave configurada,
a Comic Vine complementa os detalhes normais; erros preservam o fallback local.
Leia `docs/MVP.md` para requisitos de JDK/SDK, testes e limitações de segurança da chave no APK.

---

# 🔮 Possíveis expansões

Caso o escopo e o tempo permitam, poderão ser adicionados:

- salvamento de progresso;
- mais habilidades;
- eventos específicos por raça;
- novos inimigos;
- novos domínios;
- finais alternativos;
- maior integração com dados da Comic Vine.

Esses recursos não fazem parte obrigatoriamente da primeira versão.

---

## ⚠️ Escopo

Wunderwelt não pretende reproduzir toda a complexidade de um RPG tradicional.

A prioridade é construir uma experiência:

**simples, funcional, narrativa e coerente com o universo de Battleworld.**
