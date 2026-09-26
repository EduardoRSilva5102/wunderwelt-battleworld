package com.hexinteractive.wunderwelt.ui.story;

import com.hexinteractive.wunderwelt.model.game.*;
import com.hexinteractive.wunderwelt.utils.GameManager;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class StoryViewModelTest {
    private final GameManager game = GameManager.getInstance();
    @After public void clear() { game.clearGame(); }

    private StoryViewModel start(Region origin) {
        Attributes attributes = new Attributes();
        for (int i = 0; i < 10; i++) attributes.increase(AttributeType.STRENGTH);
        game.startNewGame(new Player("Viajante", Race.HUMAN, RaceVariant.NORMAL,
                ClassType.WARRIOR, Weapon.SWORD, origin, attributes));
        return new StoryViewModel();
    }

    private void win(StoryViewModel vm, int choice) {
        assertEquals(StoryViewModel.StoryAction.BATTLE, vm.choose(choice));
        assertTrue(vm.hasPendingBattle());
        game.finishBattle(true);
        vm.applyBattleResult();
        assertFalse(vm.hasPendingBattle());
    }

    @Test public void all96BranchCombinationsReachBothEndingsThroughFourBattles() {
        int routes = 0;
        for (Region origin : Region.values()) {
            if (!origin.isStartingRegion()) continue;
            for (int opening = 0; opening < 2; opening++)
                for (int passage = 0; passage < 2; passage++)
                    for (int layer = 0; layer < 2; layer++)
                        for (int ending = 0; ending < 2; ending++) {
                            StoryViewModel vm = start(origin);
                            vm.choose(opening);
                            win(vm, 0);
                            assertEquals("aftermath", vm.getCurrentScene().getId());
                            vm.choose(0);
                            assertEquals(origin.getPassage(), game.getStoryProgress().getCurrentRegion());
                            win(vm, passage);
                            vm.choose(0);
                            assertEquals(Region.MANHATTAN, game.getStoryProgress().getCurrentRegion());
                            vm.choose(layer);
                            win(vm, 0);
                            vm.choose(0);
                            assertEquals(Region.DOOMSTADT, game.getStoryProgress().getCurrentRegion());
                            win(vm, ending);
                            vm.choose(0);
                            assertEquals(ending == 0 ? "ending_truth" : "ending_order", vm.getCurrentScene().getId());
                            routes++;
                        }
        }
        assertEquals(96, routes);
    }

    @Test public void defeatReturnsToEncounterAndNewChoiceReplacesPreviousFlag() {
        StoryViewModel vm = start(Region.KILLVILLE);
        vm.choose(0);
        win(vm, 0);
        vm.choose(0);
        vm.choose(0);
        game.finishBattle(false);
        vm.applyBattleResult();
        assertEquals("passage", vm.getCurrentScene().getId());
        win(vm, 1);
        assertFalse(game.getStoryProgress().hasFlag("passage_investigate"));
        assertTrue(game.getStoryProgress().hasFlag("passage_negotiate"));
    }
}
