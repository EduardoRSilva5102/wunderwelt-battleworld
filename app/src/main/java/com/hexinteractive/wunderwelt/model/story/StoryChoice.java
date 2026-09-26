package com.hexinteractive.wunderwelt.model.story;

public final class StoryChoice {
    private final String label;
    private final String nextSceneId;
    private final String flag;
    private final boolean startsBattle;

    public StoryChoice(String label, String nextSceneId, String flag, boolean startsBattle) {
        this.label = label;
        this.nextSceneId = nextSceneId;
        this.flag = flag;
        this.startsBattle = startsBattle;
    }

    public String getLabel() { return label; }
    public String getNextSceneId() { return nextSceneId; }
    public String getFlag() { return flag; }
    public boolean startsBattle() { return startsBattle; }
}
