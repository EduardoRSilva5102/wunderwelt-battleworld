package com.hexinteractive.wunderwelt;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.hexinteractive.wunderwelt.model.game.Race;
import com.hexinteractive.wunderwelt.utils.GameManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MvpFlowTest {
    private void tap(int id) { onView(withId(id)).perform(scrollTo(), click()); }

    @Test public void openingFinishesAutomatically() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.skip_button)).check(matches(isDisplayed()));
            onView(isRoot()).perform(new androidx.test.espresso.ViewAction() {
                public org.hamcrest.Matcher<android.view.View> getConstraints() { return isRoot(); }
                public String getDescription() { return "Aguardar término automático do crawl"; }
                public void perform(androidx.test.espresso.UiController controller, android.view.View view) {
                    controller.loopMainThreadForAtLeast(43000);
                }
            });
            onView(withId(R.id.start_button)).check(matches(isDisplayed()));
        }
    }

    @Test public void mutantCreationCardFourBattlesAndEnding() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.skip_button)).perform(click());
            tap(R.id.start_button);
            onView(withId(R.id.name_input)).perform(replaceText("Viajante QA"), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());
            onView(withId(R.id.race_spinner)).perform(scrollTo(), click());
            onData(is(Race.MUTANT)).perform(click());
            onView(withId(R.id.variant_spinner)).check(matches(withEffectiveVisibility(Visibility.GONE)));
            tap(R.id.next_button); // class
            tap(R.id.next_button); // region
            tap(R.id.next_button); // attributes
            for (int i = 0; i < 10; i++) tap(R.id.strength_plus);
            tap(R.id.next_button);
            onView(withId(R.id.player_name)).check(matches(withText("Viajante QA")));
            tap(R.id.start_story_button);
            tap(R.id.status_button);
            onView(withId(R.id.start_story_button)).check(matches(withEffectiveVisibility(Visibility.GONE)));
            tap(R.id.edit_button);
            tap(R.id.choice_one_button);
            tap(R.id.choice_one_button);
            winBattle(scenario);
            tap(R.id.choice_one_button); // passage
            tap(R.id.choice_two_button);
            winBattle(scenario);
            tap(R.id.choice_one_button); // Manhattan
            tap(R.id.choice_two_button); // underground
            tap(R.id.choice_one_button);
            onView(withId(R.id.enemy_name)).check(matches(withText("Drácula")));
            winBattle(scenario);
            tap(R.id.choice_one_button); // Doomstadt
            tap(R.id.choice_two_button); // stability
            winBattle(scenario);
            tap(R.id.choice_one_button);
            onView(withId(R.id.choice_one_button)).check(matches(withText("Jogar novamente")));
            tap(R.id.choice_one_button);
            assertFalse(GameManager.getInstance().hasActiveGame());
            onView(withId(R.id.start_button)).check(matches(isDisplayed()));
        }
    }

    private void winBattle(ActivityScenario<MainActivity> scenario) {
        onView(withId(R.id.enemy_portrait)).check(matches(isDisplayed()));
        tap(R.id.ability_button);
        scenario.recreate(); // battle state must survive recreation
        for (int turn = 0; turn < 8; turn++) {
            boolean[] finished = {false};
            scenario.onActivity(activity -> finished[0] = activity.findViewById(R.id.result_button).getVisibility() == android.view.View.VISIBLE);
            if (finished[0]) break;
            tap(R.id.attack_button);
        }
        onView(withId(R.id.result_button)).check(matches(withText("Continuar a história")));
        tap(R.id.result_button);
    }

    @Test public void catalogDetailToggleSurvivesRecreation() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.skip_button)).perform(click());
            tap(R.id.archive_button);
            onView(withText(startsWith("Doutor Destino ↔"))).perform(scrollTo(), click());
            onView(withId(R.id.version_toggle)).perform(scrollTo(), click());
            onView(withId(R.id.character_name)).check(matches(withText("Deus Imperador Destino")));
            scenario.recreate();
            onView(withId(R.id.character_name)).check(matches(withText("Deus Imperador Destino")));
            onView(withId(R.id.version_toggle)).perform(scrollTo(), click());
            onView(withId(R.id.version_label)).check(matches(withText("NORMAL · REFERÊNCIA TERRA-616")));
        }
    }
}
