package com.hexinteractive.wunderwelt.data.model;

public class Issue {
    private long id;
    private String name;
    private String issueNumber;

    public Issue(long id, String name, String issueNumber) {
        this.id = id;
        this.name = name;
        this.issueNumber = issueNumber;
    }

    public long getId() { return id; }
    public String getName() { return name == null ? "Edição não informada" : name; }
    public String getIssueNumber() { return issueNumber == null ? "" : issueNumber; }
}
