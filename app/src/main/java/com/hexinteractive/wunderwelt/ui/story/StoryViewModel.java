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
                        new StoryChoice("Guardar o fragmento", "awaiting_battle", "kept_fragment", true));
            case "anomaly_report":
                return new StoryScene("anomaly_report", "A lei de Doom", "Oficial imperial",
                        "A autoridade toma o fragmento e ordena sua prisão. ‘Memórias anteriores ao mundo são heresia.’ "
                                + "A resposta confirma que existe algo a esconder.",
                        new StoryChoice("Recusar a prisão", "awaiting_battle", "defied_authority", true));
            case "aftermath":
                return new StoryScene("aftermath", "Depois do silêncio", player.getName(),
                        "O executor cai. Dentro de sua armadura há mapas de domínios que jamais deveriam se tocar. "
                                + "Todos os caminhos convergem para Doomstadt. A verdade pode libertar Battleworld — ou destruí-lo.",
                        new StoryChoice("Buscar a verdade, custe o que custar", "ending_truth", "chose_truth", false),
                        new StoryChoice("Descobrir como preservar o mundo", "ending_order", "chose_stability", false));
            case "ending_truth":
                return new StoryScene("ending_truth", "Rumo a Doomstadt", "Narrador",
                        player.getName() + " parte para romper a história oficial. Este é o fim do vertical slice — e o início da campanha completa.",
                        new StoryChoice("Jogar novamente", "restart", null, false));
            case "ending_order":
                return new StoryScene("ending_order", "Rumo a Doomstadt", "Narrador",
                        player.getName() + " parte em busca de uma verdade que não condene os sobreviventes. Este é o fim do vertical slice — e o início da campanha completa.",
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
        gameManager.getStoryProgress().setSceneId(choice.getNextSceneId());
        if (choice.startsBattle()) {
            gameManager.prepareBattle();
            return StoryAction.BATTLE;
        }
        return StoryAction.REFRESH;
    }

    public void applyBattleResult() {
        if (!"awaiting_battle".equals(gameManager.getStoryProgress().getSceneId())) return;
        Boolean won = gameManager.consumeBattleResult();
        if (Boolean.TRUE.equals(won)) {
            gameManager.getStoryProgress().setSceneId("aftermath");
        } else if (Boolean.FALSE.equals(won)) {
            gameManager.getStoryProgress().setSceneId("origin");
        }
    }

    private StoryScene originScene(Player player) {
        Region region = player.getRegion();
        String opening;
        switch (region) {
            case VALLEY_OF_DOOM: opening = "O sino da cidade toca sob um céu de poeira. Um objeto verde caiu além dos trilhos."; break;
            case MARVEL_1602: opening = "Os sinos da capela anunciam um presságio: metal sem forja surgiu no bosque."; break;
            case KUN_LUN: opening = "Durante a meditação, uma fissura abre-se no pátio e expõe uma máquina impossível."; break;
            case KOWLOON: opening = "A energia falha em quarenta andares. No escuro, um fragmento pulsa com memórias."; break;
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
