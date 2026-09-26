package com.hexinteractive.wunderwelt.ui.battle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hexinteractive.wunderwelt.model.game.Enemy;
import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.utils.DamageCalculator;
import com.hexinteractive.wunderwelt.utils.GameManager;

public class BattleViewModel extends ViewModel {
    private final GameManager gameManager = GameManager.getInstance();
    private final DamageCalculator damageCalculator = new DamageCalculator();
    private final MutableLiveData<BattleUiState> state = new MutableLiveData<>();
    private Player player;
    private Enemy enemy;
    private int playerHp;
    private int enemyHp;
    private boolean abilityAvailable;
    private boolean finished;
    private boolean won;

    public BattleViewModel() {
        startBattle();
    }

    public LiveData<BattleUiState> getState() {
        return state;
    }

    public void attack() {
        performAttack(false);
    }

    public void useAbility() {
        if (!abilityAvailable || finished) return;
        abilityAvailable = false;
        performAttack(true);
    }

    public void defend() {
        if (finished) return;
        int damage = damageCalculator.calculateEnemyAttack(enemy, player, true);
        playerHp = Math.max(0, playerHp - damage);
        String message = player.getName() + " assume uma posição defensiva.\n"
                + enemy.getName() + " causa " + damage + " de dano reduzido.";
        checkDefeat(message);
    }

    public void retry() {
        startBattle();
    }

    private void startBattle() {
        if (!gameManager.hasActiveGame()) {
            state.setValue(BattleUiState.invalid());
            return;
        }
        player = gameManager.getPlayer();
        enemy = gameManager.getCurrentEnemy();
        playerHp = player.getMaxHp();
        enemyHp = enemy.getMaxHp();
        abilityAvailable = true;
        finished = false;
        won = false;
        publish(enemy.getDescription());
    }

    private void performAttack(boolean ability) {
        if (finished) return;
        DamageCalculator.DamageResult result = damageCalculator.calculatePlayerAttack(player, enemy, ability);
        enemyHp = Math.max(0, enemyHp - result.getDamage());
        StringBuilder message = new StringBuilder();
        message.append(player.getName()).append(ability ? " usa sua habilidade" : " ataca com " + player.getWeapon())
                .append(" e causa ").append(result.getDamage()).append(" de dano.");
        if (result.isCritical()) message.append(" Acerto crítico!");
        if (result.hasAdvantage()) message.append(" O tipo do ataque é vantajoso.");
        if (result.hasDisadvantage()) message.append(" O alvo resiste ao tipo do ataque.");
        if (enemyHp == 0) {
            finished = true;
            won = true;
            gameManager.finishBattle(true);
            publish(message.append("\n").append(enemy.getName()).append(" foi derrotado.").toString());
            return;
        }
        int retaliation = damageCalculator.calculateEnemyAttack(enemy, player, false);
        playerHp = Math.max(0, playerHp - retaliation);
        message.append("\n").append(enemy.getName()).append(" responde e causa ").append(retaliation).append(" de dano.");
        checkDefeat(message.toString());
    }

    private void checkDefeat(String message) {
        if (playerHp == 0) {
            finished = true;
            won = false;
            gameManager.finishBattle(false);
            message += "\n" + player.getName() + " caiu. A memória de Battleworld se reorganiza...";
        }
        publish(message);
    }

    private void publish(String message) {
        state.setValue(new BattleUiState(player, enemy, playerHp, enemyHp, abilityAvailable, finished, won, message, true));
    }

    public static final class BattleUiState {
        public final Player player;
        public final Enemy enemy;
        public final int playerHp;
        public final int enemyHp;
        public final boolean abilityAvailable;
        public final boolean finished;
        public final boolean won;
        public final String message;
        public final boolean valid;

        BattleUiState(Player player, Enemy enemy, int playerHp, int enemyHp, boolean abilityAvailable,
                      boolean finished, boolean won, String message, boolean valid) {
            this.player = player;
            this.enemy = enemy;
            this.playerHp = playerHp;
            this.enemyHp = enemyHp;
            this.abilityAvailable = abilityAvailable;
            this.finished = finished;
            this.won = won;
            this.message = message;
            this.valid = valid;
        }

        static BattleUiState invalid() {
            return new BattleUiState(null, null, 0, 0, false, true, false, "Nenhuma partida ativa.", false);
        }
    }
}
