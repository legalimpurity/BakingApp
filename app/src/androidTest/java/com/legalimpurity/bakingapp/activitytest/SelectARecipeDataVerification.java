package com.legalimpurity.bakingapp.activitytest;

import android.content.ComponentName;
import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.activity.RecipeListActivity;
import com.legalimpurity.bakingapp.activity.SelectARecipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class SelectARecipeDataVerification {

    public static final String STEP_SHORT_DESCRIPTION = "Melt butter and bittersweet chocolate.";
    public static final int STEP_SHORT_DESCRIPTION_ID = 2;


    @Rule
    public ActivityTestRule<SelectARecipe> mActivityTestRule =
            new ActivityTestRule<>(SelectARecipe.class);

    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeGridView_OpensRecipeDetailsActivityOrFragmentInTwoPaneModeCheckRecyclerViewContent()
    {
        onView(withId(R.id.recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(
                withId(R.id.recipe_name),
                withTagValue(is((Object) (int) STEP_SHORT_DESCRIPTION_ID)))
        ).check(matches(withText(STEP_SHORT_DESCRIPTION)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


}
