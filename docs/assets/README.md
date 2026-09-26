# Assets do MVP

Nenhuma imagem, música, voz ou efeito sonoro foi gerado por IA. Nenhuma imagem externa foi empacotada. Os retratos e molduras adicionados são formas vetoriais/XML e estão dispensados de manifesto pelo escopo aprovado.

Android exige recursos de `res/drawable` sem subpastas arbitrárias. A organização por categoria é lógica, codificada no nome do recurso e nos IDs futuros de entrega; não mover os drawables para subpastas que quebrariam AAPT.

| Categoria | Recurso atual | ID futuro / convenção |
|---|---|---|
| characters/ | character_traveler_portrait.xml | characters/character_traveler_portrait |
| characters/ | placeholders na galeria | `CharacterPair.publicId`: characters/character_{subject}_battleworld_portrait |
| enemies/ | enemy_guard_default.xml | `Enemy.getPortraitPublicId()`: enemies/enemy_{region}_default; Drácula usa enemy_dracula_default |
| ui/ | ui_card_default.xml | ui/ui_card_default |
| branding/, splash/, intro/ | ícone nativo e textos XML | futura arte categoria_assunto_variante |
| regions/{região}/, weapons/, story/, battle/ | sem imagens externas | reservar categoria_assunto_variante ao integrar arte |

Paleta: `colors.xml` (verde latveriano, dourado imperial, marfim e carvão). Os retratos são simbólicos, não ilustrações definitivas dos personagens.

Cloudinary NÃO está conectado. Os public IDs são apenas metadados estáveis para uma futura entrega pública; sem upload, assinatura, chave secreta ou credenciais administrativas no client. Nenhum upload deve ser implementado automaticamente.

As imagens normais retornadas pela Comic Vine são remotas, exibidas em runtime com atribuição e link do registro. Não são placeholders livres, não estão redistribuídas no Git/APK e permanecem sujeitas aos direitos e termos dos titulares.

Para qualquer futura imagem local (Kenney, OpenGameArt, Game-icons.net ou outra fonte aprovada), preencher o manifesto ANTES de adicionar o arquivo:

| Arquivo / public_id | Autor | Fonte e URL específica | Licença | Data | Declaração de ausência de IA |
|---|---|---|---|---|---|

Não considerar um banco inteiro como licença: verificar o asset individual. O mapa oficial mencionado pelo autor não foi fornecido nesta rodada e não foi inventado nem baixado de fonte não autorizada.
