# Verificação do MVP — 26/09/2026

## Resultados executados

- `testDebugUnitTest`: 25 testes, zero falhas. Inclui 96 combinações de decisões narrativas dentro do teste de rotas.
- `connectedDebugAndroidTest`: 3 testes, zero falhas, em `Medium_Phone_API_36.1` (Android 16).
  - Galeria: abrir detalhes, alternar normal/Battleworld e recriar Activity.
  - Abertura: deixar o crawl terminar automaticamente e chegar ao menu.
  - Jornada: pular abertura, criar Mutante, distribuir atributos, abrir ficha, lutar quatro vezes, recriar Activity durante as batalhas, atravessar Monster Metropolis, escolher estabilidade e reiniciar.
- Consulta real à Comic Vine para `4005-1468`: sucesso, dados e URL de retrato retornados. Credencial local não registrada nos relatórios.
- `assembleDebug`: APK em `app/build/outputs/apk/debug/app-debug.apk`.
- Lint sem erros bloqueantes; warnings de internacionalização, recursos/estilo e atualizações de dependências permanecem.
- Captura de tela da criação conferida visualmente no emulador. Contraste das barras do sistema ajustado para o tema escuro.

As primeiras execuções instrumentadas falharam por instalação/aviso de ANR do **System UI do emulador**, antes das interações. Boot sem snapshot (sem wipe), remoção do aviso e redução para `--max-workers=2` permitiram a execução completa. Os três testes não foram marcados como aprovados até a repetição terminar com `OK (3 tests)`.

## Limites da verificação

Não houve teste em aparelho físico, publicação na Play Store ou auditoria de acessibilidade completa. A matriz de 96 rotas valida a máquina narrativa; o teste de interface joga uma rota representativa, não 96 partidas. Balanceamento fino permanece provisório. Não há save após encerramento do processo. Nenhum asset definitivo ou upload Cloudinary foi incluído.

## Roteiro para novas verificações

1. Instalar no Android 13+; testar Pular e término automático da abertura.
2. Criar um personagem de cada classe; voltar e avançar nos spinners sem perder seleção.
3. Selecionar Mutante, trocar de raça e voltar: o poder deve permanecer o mesmo.
4. Completar todas as origens; verificar passagem, ambas as camadas de Manhattan e os dois finais.
5. Usar habilidade apenas uma vez, provocar derrota, repetir e conferir o retorno à história.
6. Abrir todos os seis pares offline; repetir com chave válida, chave vazia, rede indisponível e resposta HTTP 429 simulada.
7. Recriar/rotacionar na abertura, criação, ficha, detalhes e combate; testar fonte ampliada e telas menores.
8. Antes de compartilhar um APK, gerar uma versão sem chave privada de desenvolvimento.
