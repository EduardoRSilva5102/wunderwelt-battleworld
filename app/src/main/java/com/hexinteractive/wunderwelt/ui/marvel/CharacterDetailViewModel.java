package com.hexinteractive.wunderwelt.ui.marvel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.repository.MarvelRepository;

public final class CharacterDetailViewModel extends ViewModel {
    private final MutableLiveData<DetailState> state = new MutableLiveData<>();
    private long loadedId = -1;

    public LiveData<DetailState> getState() { return state; }

    public void load(long characterId) {
        if (loadedId == characterId && state.getValue() != null) return;
        loadedId = characterId;
        state.setValue(new DetailState(true, null, null));
        MarvelRepository.getInstance().getCharacter(characterId, new MarvelRepository.ResultCallback<Character>() {
            @Override public void onSuccess(Character result) {
                if (loadedId == characterId) state.setValue(new DetailState(false, result, null));
            }
            @Override public void onError(String message) { state.setValue(new DetailState(false, null, message)); }
        });
    }

    public static final class DetailState {
        public final boolean loading;
        public final Character character;
        public final String error;

        DetailState(boolean loading, Character character, String error) {
            this.loading = loading;
            this.character = character;
            this.error = error;
        }
    }
}
