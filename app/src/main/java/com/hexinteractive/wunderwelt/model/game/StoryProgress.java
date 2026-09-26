package com.hexinteractive.wunderwelt.model.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class StoryProgress {
    private String sceneId = "origin";
    private int chapter = 1;
    private Region currentRegion;
    private String battleReturnScene;
    private String battleVictoryScene;
    private final Set<String> flags = new HashSet<>();

    public StoryProgress(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public String getSceneId() { return sceneId; }
    public void setSceneId(String sceneId) { this.sceneId = sceneId; }
    public int getChapter() { return chapter; }
    public void setChapter(int chapter) { this.chapter = chapter; }
    public Region getCurrentRegion() { return currentRegion; }
    public void setCurrentRegion(Region currentRegion) { this.currentRegion = currentRegion; }
    public void addFlag(String flag) {
        String[][] alternatives = {{"chose_truth", "chose_stability"},
                {"passage_investigate", "passage_negotiate"},
                {"manhattan_surface", "manhattan_underground"}};
        for (String[] pair : alternatives) {
            if (pair[0].equals(flag) || pair[1].equals(flag)) {
                flags.remove(pair[0]);
                flags.remove(pair[1]);
            }
        }
        flags.add(flag);
    }
    public boolean hasFlag(String flag) { return flags.contains(flag); }
    public Set<String> getFlags() { return Collections.unmodifiableSet(flags); }
    public void beginBattle(String returnScene, String victoryScene) {
        battleReturnScene = returnScene;
        battleVictoryScene = victoryScene;
        sceneId = "awaiting_battle";
    }
    public void resolveBattle(boolean won) {
        if (battleVictoryScene == null) return;
        sceneId = won ? battleVictoryScene : battleReturnScene;
        battleVictoryScene = null;
        battleReturnScene = null;
    }
}
