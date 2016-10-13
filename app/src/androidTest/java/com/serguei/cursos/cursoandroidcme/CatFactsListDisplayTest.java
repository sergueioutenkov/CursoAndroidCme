package com.serguei.cursos.cursoandroidcme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.serguei.cursos.cursoandroidcme.facts_list.view.CatFactsListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by serguei on 12/10/16.
 */

@RunWith(AndroidJUnit4.class)
public class CatFactsListDisplayTest {

    @Rule
    public ActivityTestRule<CatFactsListActivity> testRule = new ActivityTestRule<>(CatFactsListActivity.class);

    @Test
    public void onItemClickShouldTransitionToDetailActivity() {

        //Click en un item
        onView(withId(R.id.cat_facts_list)).perform(click());

        //Chequeamos que la imagen de la pantalla de la activity detail este visible
        onView(withId(R.id.cat_fact_image)).check(matches(isDisplayed()));
    }

    @Test
    public void onItemScrollClickAndBackShouldDisplayFactsList() {
        //Scroleamos a la posicion 29 del adapter, y hacemos click en el item
        onView(withId(R.id.cat_facts_list)).perform(RecyclerViewActions.actionOnItemAtPosition(29, click()));

        pressBack();

        //Chequeamos que la imagen de la pantalla de la activity detail este visible
        onView(withId(R.id.cat_facts_list)).check(matches(isDisplayed()));
    }
}
