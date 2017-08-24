package com.legalimpurity.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.fragments.RecipeIngredientsDescriptionFragment;
import com.legalimpurity.bakingapp.fragments.RecipeStepDescriptionFragment;
import com.legalimpurity.bakingapp.adapters.RecipeIngredientsDescriptionAdapter;
import com.legalimpurity.bakingapp.listeners.RecipeIngridentClick;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.legalimpurity.bakingapp.objects.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {


    public static final String RECIPE_OBJECT_KEY = "0a46c76c98b80b4ed6befbe3760b28b1";

    private boolean mTwoPane;
    private Recipe recipe;
    private RecipeIngredientsDescriptionAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipe_list)
    View recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        mAdapter = new RecipeIngredientsDescriptionAdapter(act, recipe, new RecipeIngridentClick() {
            @Override
            public void onRecipeIngridentCardCLick(View v, int pos) {
                if(mTwoPane) {
                    Fragment fragment;
                    if (pos == 0) {
                        fragment = RecipeIngredientsDescriptionFragment.newInstance(recipe);
                    }
                    else
                    {
                        fragment = RecipeStepDescriptionFragment.newInstance((Step) recipe.getSteps().get(pos-1));
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                }
                else
                {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailActivity.ARG_POS, pos);
                    intent.putExtra(RecipeDetailActivity.ARG_RECIPE_OBJ, recipe);
                    context.startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    private void setInitialFragment(AppCompatActivity act)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, RecipeIngredientsDescriptionFragment.newInstance(recipe))
                .commit();
    }

}
