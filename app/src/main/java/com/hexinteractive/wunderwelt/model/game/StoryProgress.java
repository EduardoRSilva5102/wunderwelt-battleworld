package com.hexinteractive.wunderwelt.model.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class StoryProgress {
    private String sceneId = "origin";
    private int chapter = 1;
    private Region currentRegion;
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
    public void addFlag(String flag) { flags.add(flag); }
    public boolean hasFlag(String flag) { return flags.contains(flag); }
    public Set<String> getFlags() { return Collections.unmodifiableSet(flags); }
}
