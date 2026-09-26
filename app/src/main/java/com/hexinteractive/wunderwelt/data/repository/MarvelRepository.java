package com.hexinteractive.wunderwelt.data.repository;

import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.model.Issue;
import com.hexinteractive.wunderwelt.data.model.Power;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class MarvelRepository {
    private static final MarvelRepository INSTANCE = new MarvelRepository();

    private final List<Character> catalog = createCatalog();

    private MarvelRepository() {
    }

    public static MarvelRepository getInstance() {
        return INSTANCE;
    }

    public boolean isConfigured() {
        return true;
    }

    public void searchCharacters(String query, ResultCallback<List<Character>> callback) {
        String normalized = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            callback.onSuccess(catalog);
            return;
        }

        List<Character> matches = new ArrayList<>();
        for (Character character : catalog) {
            if (character.getName().toLowerCase(Locale.ROOT).contains(normalized)
                    || character.getDeck().toLowerCase(Locale.ROOT).contains(normalized)) {
                matches.add(character);
            }
        }
        callback.onSuccess(Collections.unmodifiableList(matches));
    }

    public void getCharacter(long id, ResultCallback<Character> callback) {
        for (Character character : catalog) {
            if (character.getId() == id) {
                callback.onSuccess(character);
                return;
            }
        }
        callback.onError("Personagem não encontrado no catálogo local.");
    }

    private static List<Character> createCatalog() {
        return Collections.unmodifiableList(Arrays.asList(
                character(1, "Doutor Destino", "Soberano de Latvéria e mestre das artes arcanas.",
                        "Victor von Doom combina ciência, estratégia e magia. Este registro local serve como conteúdo placeholder do MVP.",
                        "Feitiçaria", "Armadura tecnológica", "Intelecto genial"),
                character(2, "Feiticeira Escarlate", "Manipuladora de energia e probabilidade.",
                        "Uma combatente capaz de alterar o rumo de conflitos inteiros. Entrada preparada para validar busca e detalhes offline.",
                        "Magia do caos", "Telecinese", "Rajadas de energia"),
                character(3, "Pantera Negra", "Protetor de Wakanda e estrategista excepcional.",
                        "T'Challa une preparo físico, inteligência e tecnologia avançada em defesa de seu povo.",
                        "Agilidade", "Estratégia", "Tecnologia de vibranium"),
                character(4, "Tempestade", "Líder mutante com domínio sobre o clima.",
                        "Ororo Munroe canaliza fenômenos atmosféricos e mantém a calma mesmo diante de ameaças globais.",
                        "Controle climático", "Voo", "Descargas elétricas"),
                character(5, "Magneto", "Mestre do magnetismo e líder mutante.",
                        "Erik Lehnsherr transforma campos magnéticos em defesa, mobilidade e poder ofensivo.",
                        "Magnetismo", "Campo de força", "Voo"),
                character(6, "Homem-Aranha", "Herói ágil das ruas de Nova York.",
                        "Peter Parker combina reflexos sobre-humanos, engenhosidade e um forte senso de responsabilidade.",
                        "Sentido aranha", "Agilidade", "Lançadores de teia")
        ));
    }

    private static Character character(long id, String name, String deck, String description,
                                       String... powers) {
        List<Power> powerList = new ArrayList<>();
        for (int i = 0; i < powers.length; i++) {
            powerList.add(new Power(id * 10 + i, powers[i]));
        }
        Issue issue = new Issue(id, "Arquivo Wunderwelt", String.valueOf(id));
        return new Character(id, name, deck, description, powerList, issue, "");
    }

    public interface ResultCallback<T> {
        void onSuccess(T result);

        void onError(String message);
    }
}
