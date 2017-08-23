package com.legalimpurity.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.fragments.RecipeStepDescriptionFragment;
import com.legalimpurity.bakingapp.objects.Recipe;

public class PotraitRecipeDetailPagerAdapter extends FragmentPagerAdapter {

    private Recipe recipe;
    private AppCompatActivity act;
    public PotraitRecipeDetailPagerAdapter(AppCompatActivity act, Recipe recipe) {
        super(act.getSupportFragmentManager());
        this.act = act;
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return RecipeStepDescriptionFragment.newInstance(recipe.getSteps().get(0));
        else
            return RecipeStepDescriptionFragment.newInstance(recipe.getSteps().get(position - 1));
    }

    @Override
    public int getCount() {
        return recipe.getSteps().size() + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return act.getResources().getString(R.string.recipe_ingredients);
        else
            return act.getResources().getString(R.string.recipe_steps_prefix,(position -1));
    }
}