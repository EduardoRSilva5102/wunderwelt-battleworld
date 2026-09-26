package com.hexinteractive.wunderwelt.utils;

import com.hexinteractive.wunderwelt.model.game.Enemy;
import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.model.game.StoryProgress;

public final class GameManager {
    private static final GameManager INSTANCE = new GameManager();

    private Player player;
    private StoryProgress storyProgress;
    private Enemy currentEnemy;
    private Boolean lastBattleWon;

    private GameManager() {
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public void startNewGame(Player player) {
        this.player = player;
        storyProgress = new StoryProgress(player.getRegion());
        currentEnemy = null;
        lastBattleWon = null;
    }

    public boolean hasActiveGame() { return player != null; }
    public Player getPlayer() { return player; }
    public StoryProgress getStoryProgress() { return storyProgress; }

    public Enemy prepareBattle() {
        currentEnemy = storyProgress.getCurrentRegion() == com.hexinteractive.wunderwelt.model.game.Region.MANHATTAN
                ? Enemy.forManhattan(storyProgress.hasFlag("manhattan_underground"))
                : Enemy.forRegion(storyProgress.getCurrentRegion());
        lastBattleWon = null;
        return currentEnemy;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy == null ? prepareBattle() : currentEnemy;
    }

    public void finishBattle(boolean won) {
        lastBattleWon = won;
    }

    public Boolean consumeBattleResult() {
        Boolean result = lastBattleWon;
        lastBattleWon = null;
        if (Boolean.TRUE.equals(result)) {
            currentEnemy = null;
        }
        return result;
    }

    public void clearGame() {
        player = null;
        storyProgress = null;
        currentEnemy = null;
        lastBattleWon = null;
    }
}
