package com.hexinteractive.wunderwelt.ui.story;

import androidx.lifecycle.ViewModel;

import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.model.game.Region;
import com.hexinteractive.wunderwelt.model.game.StoryProgress;
import com.hexinteractive.wunderwelt.model.story.StoryChoice;
import com.hexinteractive.wunderwelt.model.story.StoryScene;
import com.hexinteractive.wunderwelt.utils.GameManager;

public class StoryViewModel extends ViewModel {
    private final GameManager gameManager = GameManager.getInstance();

    public boolean hasGame() {
        return gameManager.hasActiveGame();
    }

    public StoryScene getCurrentScene() {
        Player player = gameManager.getPlayer();
        StoryProgress progress = gameManager.getStoryProgress();
        switch (progress.getSceneId()) {
            case "anomaly_direct":
                return new StoryScene("anomaly_direct", "A memória impossível", "Fragmento",
                        "Ao tocar o metal, você vê um céu que não existe em " + player.getRegion()
                                + ". Mil mundos colidem. Uma voz chama Doom de ladrão, não de criador.",
                        new StoryChoice("Guardar o fragmento", "aftermath", "kept_fragment", true));
            case "anomaly_report":
                return new StoryScene("anomaly_report", "A lei de Doom", "Oficial imperial",
                        "A autoridade toma o fragmento e ordena sua prisão. ‘Memórias anteriores ao mundo são heresia.’ "
                                + "A resposta confirma que existe algo a esconder.",
                        new StoryChoice("Recusar a prisão", "aftermath", "defied_authority", true));
            case "aftermath":
                return new StoryScene("aftermath", "Depois do silêncio", player.getName(),
                        "O executor cai. Dentro de sua armadura há mapas de domínios que jamais deveriam se tocar. "
                                + "Todos os caminhos convergem para Doomstadt. A verdade pode libertar Battleworld — ou destruí-lo.",
                        new StoryChoice("Seguir a rota para " + player.getRegion().getPassage(), "passage", "left_origin", false));
            case "passage":
                return passageScene(player);
            case "passage_after":
                return new StoryScene("passage_after", "A estrada entre os mundos", "Narrador",
                        (progress.hasFlag("passage_negotiate")
                                ? "Sua tentativa de diálogo não evitou o combate, mas deixou uma testemunha disposta a ouvir. "
                                : "O confronto abriu o caminho, mas o fragmento agora chama atenção. ")
                                + "Depois de uma longa travessia por estradas imperiais, Manhattan surge no horizonte. "
                                + "Duas cidades ocupam o mesmo lugar; abaixo delas, outras vidas resistem à luz.",
                        new StoryChoice("Entrar em Manhattan", "manhattan", "reached_manhattan", false));
            case "manhattan":
                return new StoryScene("manhattan", "Uma cidade, muitas realidades", "Narrador",
                        "Attilan paira sobre ruas costuradas de duas Terras. Você pode cruzar a Manhattan da Terra-616 "
                                + "ou descer até Monster Metropolis. O fragmento reage às duas rotas.",
                        new StoryChoice("Cruzar Manhattan — Terra-616", "manhattan_surface", "manhattan_surface", false),
                        new StoryChoice("Descer a Monster Metropolis", "manhattan_under", "manhattan_underground", false));
            case "manhattan_surface":
                return new StoryScene("manhattan_surface", "A lei acima das ruas", "Thor da patrulha",
                        "Um martelo cai diante de seus pés. O patrulheiro reconhece no fragmento uma memória proibida. "
                                + "Você terá de sobreviver ao seu julgamento para continuar.",
                        new StoryChoice("Resistir ao julgamento", "capital_road", "faced_thor", true));
            case "manhattan_under":
                return new StoryScene("manhattan_under", "O preço da noite", "Drácula",
                        "Sob as ruas de Manhattan, os conflitos de Shiklah e Drácula ecoam entre criptas. "
                                + "O vampiro percebe o poder do fragmento e exige que você o entregue como pedágio.",
                        new StoryChoice("Proteger a memória", "capital_road", "faced_dracula", true));
            case "capital_road":
                return new StoryScene("capital_road", "À vista da capital", "Narrador",
                        (progress.hasFlag("faced_dracula") ? "Você emerge dos túneis com o fragmento intacto. "
                                : "O patrulheiro permite que você leve sua questão ao trono. ")
                                + "Doomstadt não parece uma ruína de mundos: parece uma promessa. "
                                + "Agora você precisa decidir o que pedirá ao homem que a mantém de pé.",
                        new StoryChoice("Entrar em Doomstadt", "doomstadt", "reached_doomstadt", false));
            case "doomstadt":
                return new StoryScene("doomstadt", "O peso de um mundo", "Deus Imperador Destino",
                        "‘Você carrega o que restou de uma realidade. Eu carrego todas.’ "
                                + "Destino oferece uma prova: resistir ao peso de sua vontade e declarar o destino do fragmento.",
                        new StoryChoice("Exigir que a verdade seja ouvida", "doom_after", "chose_truth", true),
                        new StoryChoice("Pedir uma mudança que preserve vidas", "doom_after", "chose_stability", true));
            case "doom_after":
                return new StoryScene("doom_after", "A audiência", "Narrador",
                        "Você resiste à prova. Destino continua no trono, mas aceita ouvir aquilo que tentou silenciar. "
                                + "Sua vitória é ter atravessado o mundo e conquistado o direito de falar.",
                        new StoryChoice("Concluir a jornada", progress.hasFlag("chose_truth")
                                ? "ending_truth" : "ending_order", null, false));
            case "ending_truth":
                return new StoryScene("ending_truth", "Rumo a Doomstadt", "Narrador",
                        player.getName() + " revela a memória diante do trono. A história oficial ganha uma fissura que não pode ser fechada. "
                                + "Você deixa Doomstadt levando a verdade aos sobreviventes. Fim desta jornada.",
                        new StoryChoice("Jogar novamente", "restart", null, false));
            case "ending_order":
                return new StoryScene("ending_order", "Rumo a Doomstadt", "Narrador",
                        player.getName() + " preserva o fragmento como testemunho e exige tempo para preparar os domínios. "
                                + "A estabilidade deixa de ser silêncio: torna-se uma responsabilidade compartilhada. Fim desta jornada.",
                        new StoryChoice("Jogar novamente", "restart", null, false));
            case "origin":
            default:
                return originScene(player);
        }
    }

    public StoryAction choose(int index) {
        StoryScene scene = getCurrentScene();
        if (index < 0 || index >= scene.getChoices().size()) return StoryAction.NONE;
        StoryChoice choice = scene.getChoices().get(index);
        if (choice.getFlag() != null) gameManager.getStoryProgress().addFlag(choice.getFlag());
        if ("restart".equals(choice.getNextSceneId())) {
            gameManager.clearGame();
            return StoryAction.RESTART;
        }
        if (choice.startsBattle()) {
            gameManager.getStoryProgress().beginBattle(scene.getId(), choice.getNextSceneId());
            gameManager.prepareBattle();
            return StoryAction.BATTLE;
        }
        gameManager.getStoryProgress().setSceneId(choice.getNextSceneId());
        updateRegion(choice.getNextSceneId());
        return StoryAction.REFRESH;
    }

    public void applyBattleResult() {
        if (!"awaiting_battle".equals(gameManager.getStoryProgress().getSceneId())) return;
        Boolean won = gameManager.consumeBattleResult();
        if (won != null) gameManager.getStoryProgress().resolveBattle(won);
    }

    private void updateRegion(String sceneId) {
        StoryProgress progress = gameManager.getStoryProgress();
        if ("passage".equals(sceneId)) {
            progress.setCurrentRegion(gameManager.getPlayer().getRegion().getPassage());
            progress.setChapter(2);
        } else if ("manhattan".equals(sceneId)) {
            progress.setCurrentRegion(Region.MANHATTAN);
            progress.setChapter(3);
        } else if ("doomstadt".equals(sceneId)) {
            progress.setCurrentRegion(Region.DOOMSTADT);
            progress.setChapter(4);
        }
    }

    public String getChapterLabel() {
        StoryProgress p = gameManager.getStoryProgress();
        return "CAPÍTULO " + p.getChapter() + " · " + p.getCurrentRegion();
    }

    public boolean hasPendingBattle() {
        return hasGame() && "awaiting_battle".equals(gameManager.getStoryProgress().getSceneId());
    }

    private StoryScene passageScene(Player player) {
        Region passage = player.getRegion().getPassage();
        String text;
        switch (passage) {
            case EGYPTIA:
                text = "A poeira do Vale dá lugar às pirâmides de Egyptia. Sob a autoridade de Khonshu, "
                        + "trabalhadores transportam pedras marcadas com símbolos semelhantes aos do fragmento.";
                break;
            case UTOPOLIS:
                text = "As estradas de King James' England terminam diante de Utopolis. "
                        + "O Esquadrão Sinistro impõe sua força; Nighthawk examina as rotas de quem entra.";
                break;
            case MONARCHY_OF_M:
                text = "Ao deixar Killville, você encontra a monarquia de Magnus. "
                        + "O caminho parece curto, mas Mercúrio intercepta viajantes antes que alcancem a saída.";
                break;
            case WASTELANDS:
                text = "O neon de 2099 desaparece nos ermos. Carcaças e estradas vazias contam outra história de poder. "
                        + "Um integrante do Bando Hulk ocupa a única passagem segura à vista.";
                break;
            case THE_REGENCY:
            default:
                text = (player.getRegion() == Region.KUN_LUN
                        ? "A disciplina dos mosteiros encontra um silêncio imposto pela força. "
                        : "Você reconhece no aparato de vigilância ecos da quarentena de Tecnópolis. ")
                        + "Em The Regency, Regent transforma poderes em instrumentos de controle. "
                        + "Sua armadura detecta a energia do fragmento.";
        }
        return new StoryScene("passage", passage.toString(), "Narrador", text,
                new StoryChoice("Investigar e enfrentar o bloqueio", "passage_after", "passage_investigate", true),
                new StoryChoice("Tentar negociar a travessia", "passage_after", "passage_negotiate", true));
    }

    private StoryScene originScene(Player player) {
        Region region = player.getRegion();
        String opening;
        switch (region) {
            case VALLEY_OF_DOOM: opening = "O sino da cidade toca sob um céu de poeira. Um objeto verde caiu além dos trilhos."; break;
            case KING_JAMES_ENGLAND: opening = "Os sinos da capela anunciam um presságio: metal sem forja surgiu no bosque."; break;
            case KUN_LUN: opening = "Durante a meditação, uma fissura abre-se no pátio e expõe uma máquina impossível."; break;
            case KILLVILLE: opening = "A energia falha em quarenta andares. No escuro, um fragmento pulsa com memórias."; break;
            case NUEVA_YORK_2099: opening = "A rede corporativa transmite por um segundo imagens de universos mortos."; break;
            case TECHNOPOLIS:
            default: opening = "Os sensores da quarentena detectam uma peça que não pertence a nenhuma tecnologia conhecida."; break;
        }
        return new StoryScene("origin", region.toString(), "Narrador",
                opening + " " + player.getName() + ", " + player.getRace() + " e " + player.getClassType()
                        + ", percebe que os guardas de Doom já estão a caminho.",
                new StoryChoice("Investigar antes que eles cheguem", "anomaly_direct", "investigated", false),
                new StoryChoice("Entregar o achado à autoridade", "anomaly_report", "reported", false));
    }

    public enum StoryAction { NONE, REFRESH, BATTLE, RESTART }
}
