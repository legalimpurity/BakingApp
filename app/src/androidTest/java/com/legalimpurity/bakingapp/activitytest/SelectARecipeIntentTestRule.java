package com.legalimpurity.bakingapp.activitytest;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.activity.RecipeListActivity;
import com.legalimpurity.bakingapp.activity.SelectARecipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by rajatkhanna on 26/08/17.
 */

public class SelectARecipeIntentTestRule {

    @Rule
    public IntentsTestRule<SelectARecipe> mActivityTestRule =
            new IntentsTestRule<>(SelectARecipe.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeGridView_CheckIntentExtras()
    {
        onView(withId(R.id.recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasExtraWithKey(RecipeListActivity.RECIPE_OBJECT_KEY));
    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
