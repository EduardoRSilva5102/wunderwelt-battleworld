package com.hexinteractive.wunderwelt.ui.character;

import androidx.lifecycle.ViewModel;

import com.hexinteractive.wunderwelt.model.game.AttributeType;
import com.hexinteractive.wunderwelt.model.game.Attributes;
import com.hexinteractive.wunderwelt.model.game.ClassType;
import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.model.game.Race;
import com.hexinteractive.wunderwelt.model.game.RaceVariant;
import com.hexinteractive.wunderwelt.model.game.Region;
import com.hexinteractive.wunderwelt.model.game.Weapon;

public class CharacterViewModel extends ViewModel {
    private String name = "";
    private Race race = Race.HUMAN;
    private RaceVariant raceVariant = RaceVariant.NORMAL;
    private ClassType classType = ClassType.WARRIOR;
    private Weapon weapon = Weapon.SWORD;
    private Region region = Region.VALLEY_OF_DOOM;
    private Attributes attributes = new Attributes();

    public String getName() { return name; }
    public void setName(String name) { this.name = name == null ? "" : name.trim(); }
    public Race getRace() { return race; }
    public RaceVariant getRaceVariant() { return raceVariant; }
    public ClassType getClassType() { return classType; }
    public Weapon getWeapon() { return weapon; }
    public Region getRegion() { return region; }
    public Attributes getAttributes() { return attributes; }

    public void setRace(Race race) {
        this.race = race;
        if (raceVariant == null || raceVariant.getRace() != race) {
            java.util.List<RaceVariant> variants = RaceVariant.forRace(race);
            raceVariant = variants.isEmpty() ? null : variants.get(0);
        }
    }

    public void setRaceVariant(RaceVariant raceVariant) {
        if (raceVariant != null && raceVariant.getRace() != race) {
            throw new IllegalArgumentException("A variante não pertence à raça selecionada.");
        }
        this.raceVariant = raceVariant;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
        if (weapon == null || weapon.getClassType() != classType) {
            weapon = Weapon.forClass(classType).get(0);
        }
    }

    public void setWeapon(Weapon weapon) {
        if (weapon.getClassType() != classType) {
            throw new IllegalArgumentException("A arma não pertence à classe selecionada.");
        }
        this.weapon = weapon;
    }

    public void setRegion(Region region) {
        if (!region.isStartingRegion()) {
            throw new IllegalArgumentException("Escolha uma região inicial.");
        }
        this.region = region;
    }

    public boolean increaseAttribute(AttributeType type) {
        return attributes.increase(type);
    }

    public boolean decreaseAttribute(AttributeType type) {
        return attributes.decrease(type);
    }

    public boolean canBuildPlayer() {
        return !name.isEmpty() && race != null && classType != null && weapon != null
                && region != null && attributes.isComplete();
    }

    public Player buildPlayer() {
        if (!canBuildPlayer()) {
            throw new IllegalStateException("A ficha do personagem ainda está incompleta.");
        }
        return new Player(name, race, raceVariant, classType, weapon, region, attributes);
    }

    public void reset() {
        name = "";
        race = Race.HUMAN;
        raceVariant = RaceVariant.NORMAL;
        classType = ClassType.WARRIOR;
        weapon = Weapon.SWORD;
        region = Region.VALLEY_OF_DOOM;
        attributes = new Attributes();
    }
}
