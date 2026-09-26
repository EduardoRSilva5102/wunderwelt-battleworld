package com.hexinteractive.wunderwelt.data.repository;

import com.hexinteractive.wunderwelt.data.api.ComicVineApi;
import com.hexinteractive.wunderwelt.data.model.Character;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static org.junit.Assert.*;

public class ComicVineIntegrationTest {
    private MockWebServer server;
    private CountDownLatch completed;
    private MarvelRepository repository;
    @Before public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        completed = new CountDownLatch(1);
        ComicVineApi api = new Retrofit.Builder().baseUrl(server.url("/api/"))
                .client(new OkHttpClient.Builder().callTimeout(2, TimeUnit.SECONDS).build())
                .callbackExecutor(command -> { try { command.run(); } finally { completed.countDown(); } })
                .addConverterFactory(GsonConverterFactory.create()).build().create(ComicVineApi.class);
        repository = new MarvelRepository(api);
    }
    @After public void stop() throws Exception { server.shutdown(); }
    private <T> MarvelRepository.ResultCallback<T> capture(AtomicReference<T> result) {
        return new MarvelRepository.ResultCallback<T>() {
            public void onSuccess(T value) { result.set(value); }
            public void onError(String error) { throw new AssertionError(error); }
        };
    }
    @Test public void detailUsesRealEndpointAndParsesSnakeCaseMetadata() throws Exception {
        server.enqueue(new MockResponse().setBody("{\"status_code\":1,\"results\":{\"id\":1468,\"name\":\"Doctor Doom\",\"deck\":\"Remote summary\",\"image\":{\"small_url\":\"https://comicvine.gamespot.com/portrait.jpg\"},\"powers\":[{\"id\":1,\"name\":\"Intellect\"}],\"first_appeared_in_issue\":{\"name\":\"Fantastic Four\",\"issue_number\":\"5\"}}}"));
        AtomicReference<Character> result = new AtomicReference<>();
        repository.getCharacter(1468, capture(result));
        assertTrue(completed.await(5, TimeUnit.SECONDS));
        assertTrue(result.get().isRemote());
        assertEquals("Doctor Doom", result.get().getName());
        assertEquals("Intellect", result.get().getPowers().get(0).getName());
        assertEquals("5", result.get().getFirstAppearedInIssue().getIssueNumber());
        assertTrue(result.get().getImageUrl().endsWith("portrait.jpg"));
        assertTrue(server.takeRequest().getPath().startsWith("/api/character/4005-1468/"));
    }
    @Test public void httpLimitKeepsLocalDetail() throws Exception {
        fallback(new MockResponse().setResponseCode(429));
    }
    @Test public void apiLimitKeepsLocalDetail() throws Exception {
        fallback(new MockResponse().setBody("{\"status_code\":107,\"results\":null}"));
    }
    @Test public void malformedJsonKeepsLocalDetail() throws Exception {
        fallback(new MockResponse().setBody("not json"));
    }
    @Test public void mismatchedIdentityKeepsLocalDetail() throws Exception {
        fallback(new MockResponse().setBody("{\"status_code\":1,\"results\":{\"id\":9999,\"name\":\"Wrong Doom\"}}"));
    }
    @Test public void noConnectionStillProvidesLocalDetail() throws Exception {
        server.shutdown();
        AtomicReference<Character> result = new AtomicReference<>();
        repository.getCharacter(1468, capture(result));
        assertTrue(completed.await(5, TimeUnit.SECONDS));
        assertEquals("Doutor Destino", result.get().getName());
    }
    private void fallback(MockResponse response) throws Exception {
        server.enqueue(response);
        AtomicReference<Character> result = new AtomicReference<>();
        repository.getCharacter(1468, capture(result));
        assertTrue(completed.await(5, TimeUnit.SECONDS));
        assertFalse(result.get().isRemote());
        assertEquals("Doutor Destino", result.get().getName());
    }
    @Test public void remoteSearchOnlyAcceptsCuratedIdentities() throws Exception {
        server.enqueue(new MockResponse().setBody("{\"status_code\":1,\"results\":[{\"id\":1468,\"name\":\"Doctor Doom\"},{\"id\":9999,\"name\":\"Variant\"}]}"));
        AtomicReference<List<Character>> result = new AtomicReference<>();
        repository.searchCharacters("Victor", capture(result));
        assertTrue(completed.await(5, TimeUnit.SECONDS));
        assertEquals(1, result.get().size());
        assertEquals(1468, result.get().get(0).getId());
        assertTrue(server.takeRequest().getPath().contains("query=Victor"));
    }
    @Test public void allSixPairsHaveUniqueIdsAndLocalLore() {
        java.util.Set<Long> ids = new java.util.HashSet<>();
        for (com.hexinteractive.wunderwelt.data.model.CharacterPair pair : CharacterCatalog.PAIRS) {
            assertTrue(ids.add(pair.normal.getId()));
            assertTrue(pair.lore.length() > 100);
            assertTrue(pair.publicId.startsWith("characters/character_"));
            assertFalse(pair.normal.getPowers().isEmpty());
        }
        assertEquals(6, ids.size());
    }
}
