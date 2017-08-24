package com.legalimpurity.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.fragments.RecipeIngredientsDescriptionFragment;
import com.legalimpurity.bakingapp.fragments.RecipeStepDescriptionFragment;
import com.legalimpurity.bakingapp.objects.Recipe;

public class PotraitRecipeDetailPagerAdapter extends FragmentPagerAdapter {

    private Recipe recipe;
    private AppCompatActivity act;
    private int itemToBeSelected;
    public PotraitRecipeDetailPagerAdapter(AppCompatActivity act, Recipe recipe, int itemToBeSelected) {
        super(act.getSupportFragmentManager());
        this.act = act;
        this.recipe = recipe;
        this.itemToBeSelected = itemToBeSelected;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return RecipeIngredientsDescriptionFragment.newInstance(recipe);
        else {
            boolean temp = false;
            if(itemToBeSelected == position)
                temp = true;
            else
                temp = false;
            return RecipeStepDescriptionFragment.newInstance(recipe.getSteps().get(position - 1),temp);
        }
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
            return act.getResources().getString(R.string.recipe_steps_prefix,(position));
    }
}