package com.hexinteractive.wunderwelt.ui.marvel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.repository.MarvelRepository;

import java.util.Collections;
import java.util.List;

public class MarvelViewModel extends ViewModel {
    private final MarvelRepository repository = MarvelRepository.getInstance();
    private final MutableLiveData<SearchState> state = new MutableLiveData<>(SearchState.idle());

    public MarvelViewModel() {
        search("");
    }

    public LiveData<SearchState> getState() { return state; }

    public void search(String query) {
        String normalized = query == null ? "" : query.trim();
        state.setValue(SearchState.loading());
        repository.searchCharacters(normalized, new MarvelRepository.ResultCallback<List<Character>>() {
            @Override public void onSuccess(List<Character> result) {
                if (result.isEmpty()) state.setValue(SearchState.error("Nenhum personagem encontrado."));
                else state.setValue(SearchState.success(result));
            }
            @Override public void onError(String message) { state.setValue(SearchState.error(message)); }
        });
    }

    public static final class SearchState {
        public final boolean loading;
        public final String message;
        public final List<Character> results;

        private SearchState(boolean loading, String message, List<Character> results) {
            this.loading = loading;
            this.message = message;
            this.results = results;
        }

        static SearchState idle() { return new SearchState(false, "Carregando catálogo local...", Collections.emptyList()); }
        static SearchState loading() { return new SearchState(true, "Consultando o catálogo local...", Collections.emptyList()); }
        static SearchState error(String message) { return new SearchState(false, message, Collections.emptyList()); }
        static SearchState success(List<Character> results) { return new SearchState(false, results.size() + " personagem(ns) disponível(is) offline", results); }
    }
}
