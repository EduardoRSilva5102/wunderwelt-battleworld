package com.hexinteractive.wunderwelt.data.model;

import java.util.Collections;
import java.util.List;

public final class ComicVineListResponse<T> {
    private int statusCode;
    private String error;
    private int totalResults;
    private List<T> results;

    public boolean isSuccessful() { return statusCode == 1; }
    public String getError() { return error == null ? "Erro desconhecido" : error; }
    public int getTotalResults() { return totalResults; }
    public List<T> getResults() { return results == null ? Collections.emptyList() : results; }
}
