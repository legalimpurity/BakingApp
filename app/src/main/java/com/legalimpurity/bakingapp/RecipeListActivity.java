package com.legalimpurity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.legalimpurity.bakingapp.adapters.RecipeIngridientsDescriptionAdapter;
import com.legalimpurity.bakingapp.listeners.RecipeIngridentClick;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.legalimpurity.bakingapp.objects.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {


    public static final String RECIPE_OBJECT_KEY = "0a46c76c98b80b4ed6befbe3760b28b1";

    private boolean mTwoPane;
    private Recipe recipe;
    private RecipeIngridientsDescriptionAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipe_list)
    View recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        processFlow(this,savedInstanceState);

        assert recyclerView != null;
        setupRecyclerView(this, (RecyclerView) recyclerView);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

        if(mTwoPane)
            setInitialFragment(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_OBJECT_KEY,recipe);
    }


    private void processFlow(AppCompatActivity act, Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_OBJECT_KEY)) {
                recipe = (Recipe) getIntent().getExtras().getParcelable(RECIPE_OBJECT_KEY);
            }
        }
        else if(getIntent() != null && getIntent().getExtras() != null) {
            recipe = (Recipe) getIntent().getExtras().getParcelable(RECIPE_OBJECT_KEY);
        }
        else
            NavUtils.navigateUpFromSameTask(act);
    }


    private void setupRecyclerView(final AppCompatActivity act, @NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(act));

        recyclerView.setHasFixedSize(true);

        mAdapter = new RecipeIngridientsDescriptionAdapter(act, recipe, new RecipeIngridentClick() {
            @Override
            public void onRecipeIngridentCardCLick(View v, Object recipeOrStep) {
                if(mTwoPane) {
                    if (recipeOrStep instanceof Step) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(RecipeStepDescriptionFragment.ARG_STEP_OBJ, (Step) recipeOrStep);
                        RecipeStepDescriptionFragment fragment = new RecipeStepDescriptionFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    }
                }
                else
                {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeStepDescriptionFragment.ARG_STEP_OBJ, (Parcelable) recipeOrStep);
                    context.startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    private void setInitialFragment(AppCompatActivity act)
    {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepDescriptionFragment.ARG_STEP_OBJ, recipe.getSteps().get(0));
        RecipeStepDescriptionFragment fragment = new RecipeStepDescriptionFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .commit();
    }

}
