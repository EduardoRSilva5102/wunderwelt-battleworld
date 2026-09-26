package com.hexinteractive.wunderwelt.model.story;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class StoryScene {
    private final String id;
    private final String title;
    private final String narrator;
    private final String text;
    private final List<StoryChoice> choices;

    public StoryScene(String id, String title, String narrator, String text, StoryChoice... choices) {
        this.id = id;
        this.title = title;
        this.narrator = narrator;
        this.text = text;
        this.choices = choices.length == 0 ? Collections.emptyList() : Arrays.asList(choices);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getNarrator() { return narrator; }
    public String getText() { return text; }
    public List<StoryChoice> getChoices() { return choices; }
}
