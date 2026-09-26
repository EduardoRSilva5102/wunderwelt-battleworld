package com.hexinteractive.wunderwelt.data.repository;
import com.hexinteractive.wunderwelt.data.api.ApiClient;
import com.hexinteractive.wunderwelt.data.api.ComicVineApi;
import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.model.CharacterPair;
import com.hexinteractive.wunderwelt.data.model.ComicVineDetailResponse;
import com.hexinteractive.wunderwelt.data.model.ComicVineListResponse;
import java.text.Normalizer;
import java.util.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class MarvelRepository {
    private static final class Holder { static final MarvelRepository INSTANCE = new MarvelRepository(ApiClient.create()); }
    private final ComicVineApi api;
    private final Map<Long, Character> cache = new java.util.concurrent.ConcurrentHashMap<>();
    public MarvelRepository(ComicVineApi api) { this.api = api; }
    public static MarvelRepository getInstance() { return Holder.INSTANCE; }
    public boolean isConfigured() { return api != null; }

    public void searchCharacters(String query, ResultCallback<List<Character>> callback) {
        String normalized = normalize(query);
        List<Character> local = new ArrayList<>();
        for (CharacterPair pair : CharacterCatalog.PAIRS) {
            String searchable = pair.normal.getName() + " " + pair.normal.getDeck() + " " + pair.searchName
                    + " " + pair.battleworldName + " " + pair.region;
            if (normalize(searchable).contains(normalized)) local.add(pair.normal);
        }
        callback.onSuccess(Collections.unmodifiableList(local));
        if (api == null || normalized.isEmpty()) return;
        api.search(query).enqueue(new Callback<ComicVineListResponse<Character>>() {
            @Override public void onResponse(Call<ComicVineListResponse<Character>> call, Response<ComicVineListResponse<Character>> response) {
                ComicVineListResponse<Character> body = response.body();
                if (!response.isSuccessful() || body == null || !body.isSuccessful()) return;
                Map<Long, Character> matches = new LinkedHashMap<>();
                for (Character value : local) matches.put(value.getId(), value);
                // Only explicit curated IDs can become pairs; never guess from a similar name.
                for (Character value : body.getResults()) {
                    if (value == null) continue;
                    CharacterPair pair = CharacterCatalog.find(value.getId());
                    if (pair != null) matches.put(value.getId(), pair.normal);
                }
                callback.onSuccess(Collections.unmodifiableList(new ArrayList<>(matches.values())));
            }
            @Override public void onFailure(Call<ComicVineListResponse<Character>> call, Throwable error) { /* local result already shown */ }
        });
    }

    public void getCharacter(long id, ResultCallback<Character> callback) {
        CharacterPair pair = CharacterCatalog.find(id);
        if (pair == null) { callback.onError("Personagem não encontrado no catálogo curado."); return; }
        Character cached = cache.get(id);
        callback.onSuccess(cached == null ? pair.normal : cached);
        if (api == null || cached != null) return;
        api.character(id).enqueue(new Callback<ComicVineDetailResponse<Character>>() {
            @Override public void onResponse(Call<ComicVineDetailResponse<Character>> call, Response<ComicVineDetailResponse<Character>> response) {
                ComicVineDetailResponse<Character> body = response.body();
                if (!response.isSuccessful() || body == null || !body.isSuccessful() || body.getResults() == null) return;
                Character remote = body.getResults();
                if (remote.getId() != id) return;
                remote.markRemote();
                cache.put(id, remote);
                callback.onSuccess(remote);
            }
            @Override public void onFailure(Call<ComicVineDetailResponse<Character>> call, Throwable error) { /* offline result remains usable */ }
        });
    }
    private static String normalize(String value) {
        return Normalizer.normalize(value == null ? "" : value.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }
    public interface ResultCallback<T> {
        void onSuccess(T result);
        void onError(String message);
    }
}
