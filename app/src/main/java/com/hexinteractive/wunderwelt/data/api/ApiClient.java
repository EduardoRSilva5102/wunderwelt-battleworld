package com.hexinteractive.wunderwelt.data.api;

/**
 * Reservado para a integração remota posterior ao MVP. O catálogo jogável atual
 * é deliberadamente offline, portanto não inicializa clientes de rede.
 */
public final class ApiClient {
    public static final String COMIC_VINE_BASE_URL = "https://comicvine.gamespot.com/api/";

    private ApiClient() {
    }
}
