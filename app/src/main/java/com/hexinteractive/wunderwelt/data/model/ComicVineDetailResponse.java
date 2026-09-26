package com.hexinteractive.wunderwelt.data.model;

public final class ComicVineDetailResponse<T> {
    @com.google.gson.annotations.SerializedName("status_code") private int statusCode;
    private String error;
    private T results;

    public boolean isSuccessful() { return statusCode == 1; }
    public String getError() { return error == null ? "Erro desconhecido" : error; }
    public T getResults() { return results; }
}
