package com.legalimpurity.bakingapp.activitytest;

import android.content.ComponentName;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.activity.RecipeListActivity;
import com.legalimpurity.bakingapp.activity.SelectARecipe;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by rajatkhanna on 26/08/17.
 */

public class SelectARecipeIntentVerification {


    @Rule
    public IntentsTestRule<SelectARecipe> mActivityTestRule =
            new IntentsTestRule<>(SelectARecipe.class);

    @Test
    public void clickRecipeGridView_CheckIntentExtras()
    {
        Intents.init();
        onView(withId(R.id.recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(SelectARecipe.class.getName()));
        Intents.release();
    }

}
