package com.hexinteractive.wunderwelt.data.repository;
import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.model.CharacterPair;
import com.hexinteractive.wunderwelt.data.model.Power;
import java.util.*;

public final class CharacterCatalog {
    private CharacterCatalog() { }
    public static final List<CharacterPair> PAIRS = Collections.unmodifiableList(Arrays.asList(
        pair(1468, "Doutor Destino", "Doctor Doom", "Ciência e magia a serviço de Victor von Doom.",
            "Deus Imperador Destino", "Doomstadt",
            "Destino reuniu os restos do multiverso em Battleworld. No arquivo do viajante, o trono representa uma pergunta: preservar vidas justifica esconder de onde elas vieram?", "doom",
            "Feitiçaria", "Armadura tecnológica", "Intelecto genial"),
        pair(1456, "Doutor Estranho", "Doctor Strange", "Stephen Strange protege a realidade com artes místicas.",
            "Xerife Estranho", "Doomstadt",
            "Estranho atua como autoridade mística do mundo de Destino. Para o viajante, sua presença evidencia a tensão entre conhecer a verdade e sustentar a ordem.", "strange",
            "Feitiçaria", "Projeção astral", "Proteção mística"),
        pair(1441, "Magneto", "Magneto", "Líder mutante capaz de manipular campos magnéticos.",
            "Rei Magnus", "The Monarchy of M",
            "A Casa de Magnus ocupa o centro da monarquia mutante. Neste arquivo, o poder de Magneto é também uma fronteira: atravessá-la exige entender quem pode circular livremente.", "magneto",
            "Magnetismo", "Voo", "Campo de força"),
        pair(1443, "Homem-Aranha", "Spider-Man", "Peter Parker une reflexos extraordinários e responsabilidade.",
            "Peter Parker — Renew Your Vows", "The Regency",
            "Sob o domínio de Regent, Peter precisa proteger sua família em um mundo que persegue poderes. Seu registro lembra ao viajante que sobreviver pode exigir coragem longe dos grandes confrontos.", "spiderman",
            "Sentido aranha", "Agilidade", "Força sobre-humana"),
        pair(1455, "Homem de Ferro", "Iron Man", "Tony Stark transforma engenharia em armaduras avançadas.",
            "Tony Stark — Tecnópolis", "Tecnópolis",
            "Em Tecnópolis, a vida depende de tecnologia e armaduras. O arquivo do viajante apresenta Tony como parte de um sistema em que proteção e controle são difíceis de separar.", "ironman",
            "Armadura tecnológica", "Voo", "Intelecto genial"),
        pair(3709, "M.O.D.O.K.", "MODOK", "Intelecto ampliado e armamento em uma plataforma flutuante.",
            "M.O.D.O.K. — Assassino", "Killville",
            "Killville é o território das oportunidades letais de M.O.D.O.K. No arquivo do viajante, ele personifica o custo de viver onde cada caminho pode se tornar um contrato.", "modok",
            "Intelecto ampliado", "Energia psíquica", "Armamento tecnológico")
    ));
    private static CharacterPair pair(long id, String name, String search, String deck, String title,
                                      String region, String lore, String subject, String... powers) {
        List<Power> entries = new ArrayList<>();
        for (int i = 0; i < powers.length; i++) entries.add(new Power(i, powers[i]));
        return new CharacterPair(new Character(id, name, deck, "Resumo original do catálogo offline.", entries, null,
                "https://comicvine.gamespot.com/character/4005-" + id + "/"), search, title, region, lore, subject);
    }
    public static CharacterPair find(long id) {
        for (CharacterPair pair : PAIRS) if (pair.normal.getId() == id) return pair;
        return null;
    }
}
