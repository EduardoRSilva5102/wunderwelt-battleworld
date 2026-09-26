# MVP implementado

## Fluxo e escopo

Splash nativo → crawl de 42 segundos, pulável → menu → criação → ficha → origem → passagem → Manhattan → Doomstadt → um de dois desfechos.

- Java 11, Views/XML, Fragments, MVVM, uma Activity; Android 13/API 33 ou superior.
- Mutante recebe um de sete poderes, sorteado uma única vez por criação. Voltar de tela ou trocar temporariamente de raça não permite novo sorteio. Fator de cura dá +5 HP máximos; absorção cinética reduz em 1 o dano recebido (mínimo 1); poderes ativos alteram o tipo da habilidade, não dos ataques normais. Pontos de atributos continuam separados.
- Ficha com retrato XML e acesso pelo botão “Ficha do viajante”.
- Quatro batalhas por jornada, HP restaurado entre encontros e habilidade disponível uma vez por batalha. Retrato provisório, animações, nomes por classe, prévia de afinidade e logs variados.
- Duas rotas em Manhattan: superfície com patrulha Thor ou Monster Metropolis com Drácula. A prova final não significa eliminar o poder divino de Destino.
- Escolhas de negociar ainda levam ao combate, conforme o rótulo/texto da cena; alteram flags e narrativa, não introduzem uma mecânica de diplomacia.
- Campanha em memória: recriação de Activity preserva ViewModels; encerrar o processo perde a partida. Sem save, login, XP, inventário, party ou backend.

| Origem | Passagem |
|---|---|
| Valley of Doom | Egyptia |
| King James' England | Utopolis |
| K'un-Lun | The Regency |
| Killville | The Monarchy of M |
| Nueva York 2099 | The Wastelands |
| Technopolis | The Regency |

The Regency reaproveita a cena, variando a introdução conforme a origem. Todas as passagens convergem em Manhattan e depois Doomstadt. As longas viagens intermediárias são elididas, não declaradas como fronteiras diretas.

## Comic Vine

Os seis pares curados são Destino, Estranho, Magneto, Peter Parker, Tony Stark e M.O.D.O.K. Identidades remotas são aceitas somente pelos IDs fixados em `CharacterCatalog`. Inimigos originais genéricos não são associados arbitrariamente a heróis conhecidos.

`MarvelRepository` entrega o resultado local imediatamente. Busca não vazia usa `/search/`; detalhe usa `/character/4005-{id}/`, via Retrofit, OkHttp e Gson. A lista remota só acrescenta identidades curadas. Detalhes válidos ficam em cache de memória; sem chave, rede, resposta válida ou em caso de limite, o local permanece visível. Não há retry automático. A aba Battleworld nunca depende da API.

O registro principal da Comic Vine pode incluir menções a outras realidades; a UI informa essa limitação e não afirma que a API filtra uma Terra específica. Nome, resumo, poderes, primeira aparição e imagem são remotos quando disponíveis. A imagem usa cliente sem autenticação, HTTPS, domínios permitidos, timeout, limite de 2 MiB e redução de resolução; erro mantém o retrato XML.

`local.properties` (ignorado pelo Git):

```properties
sdk.dir=SEU_CAMINHO_DO_SDK
COMIC_VINE_API=SUA_CHAVE_OPCIONAL
```

Apenas `COMIC_VINE_API` é lida para BuildConfig. Nenhum segredo Cloudinary ou credencial administrativa é incorporado. Uma chave compilada em Android pode ser extraída do APK: use chave de desenvolvimento própria e não distribua APK com credencial que precise permanecer secreta. Um proxy autenticado é evolução futura, fora do MVP. Não há logs HTTP com URLs autenticadas. Não usamos a API da Marvel: a integração desta rodada é Comic Vine.

## Build e testes

Use o Gradle Wrapper 9.1.0, AGP 9.0.1, JDK 17+ compatível com AGP (JBR do Android Studio), SDK 36. A linguagem do app é Java 11. Não precisa de chave para build/jogar; o primeiro build precisa de internet para baixar dependências.

```powershell
.\gradlew.bat testDebugUnitTest lintDebug assembleDebug
# Com emulador ou dispositivo conectado:
.\gradlew.bat connectedDebugAndroidTest
```

Neste Windows, foi necessário configurar o processo Java para contornar erro de loopback:

```powershell
$env:JAVA_TOOL_OPTIONS='-Djava.net.preferIPv4Stack=true -Djdk.net.unixdomain.tmpdir=C:\Windows\Temp'
.\gradlew.bat testDebugUnitTest lintDebug assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`. Relatórios: `app/build/reports/`.

Os testes unitários cobrem atributos, validação, poderes, afinidades, 96 combinações narrativas, repetição após derrota, catálogo e HTTP simulado (sucesso, identidade errada, 429, limite da API, JSON inválido e rede indisponível). Os testes de interface estão em `MvpFlowTest`. O lint ainda sinaliza dívida de internacionalização/estilo e versões de dependências; não é correto chamar o projeto de livre de warnings.

Uma consulta real ao detalhe `4005-1468` com a configuração local retornou `status_code=1`, nome, poderes e URL de retrato em 26/09/2026. Isso valida a disponibilidade do serviço nessa execução, não garante disponibilidade futura nem substitui testes de fallback.

## Organização

Integração em `feat/project-mvp`, com commits separados de build/base, mutantes, ficha, campanha, combate, abertura, catálogo e verificação/documentação. Pushes incrementais, sem PR criada. Balanceamento continua provisório em `Constants` e `DamageCalculator`.
