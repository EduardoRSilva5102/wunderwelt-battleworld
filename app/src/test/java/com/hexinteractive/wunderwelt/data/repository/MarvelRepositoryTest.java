package com.hexinteractive.wunderwelt.data.repository;

import com.hexinteractive.wunderwelt.data.model.Character;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarvelRepositoryTest {
    private final MarvelRepository repository = MarvelRepository.getInstance();

    @Test
    public void emptySearchReturnsTheOfflineCatalog() {
        AtomicReference<List<Character>> result = new AtomicReference<>();

        repository.searchCharacters("", callback(result));

        assertNotNull(result.get());
        assertEquals(6, result.get().size());
    }

    @Test
    public void searchResultCanBeOpenedInDetails() {
        AtomicReference<List<Character>> searchResult = new AtomicReference<>();
        repository.searchCharacters("Destino", callback(searchResult));
        assertNotNull(searchResult.get());
        assertEquals(1, searchResult.get().size());

        AtomicReference<Character> detailResult = new AtomicReference<>();
        repository.getCharacter(searchResult.get().get(0).getId(), callback(detailResult));

        assertNotNull(detailResult.get());
        assertEquals("Doutor Destino", detailResult.get().getName());
        assertTrue(detailResult.get().getPowers().size() >= 3);
    }

    private static <T> MarvelRepository.ResultCallback<T> callback(AtomicReference<T> result) {
        return new MarvelRepository.ResultCallback<T>() {
            @Override
            public void onSuccess(T value) {
                result.set(value);
            }

            @Override
            public void onError(String message) {
                throw new AssertionError(message);
            }
        };
    }
}
