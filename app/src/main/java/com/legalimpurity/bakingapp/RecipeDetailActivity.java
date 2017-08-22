package com.legalimpurity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.legalimpurity.bakingapp.objects.Step;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // else fragment stays in device.
        if (savedInstanceState == null) {
            if(getIntent() != null && getIntent().getParcelableExtra(RecipeStepDescriptionFragment.ARG_STEP_OBJ) != null) {
                Object receivedObj = getIntent().getParcelableExtra(RecipeStepDescriptionFragment.ARG_STEP_OBJ);
                if (receivedObj instanceof Step) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeStepDescriptionFragment.ARG_STEP_OBJ, (Step) receivedObj);
                    RecipeStepDescriptionFragment fragment = new RecipeStepDescriptionFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.recipe_detail_container, fragment)
                            .commit();
                }
                else
                {

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
