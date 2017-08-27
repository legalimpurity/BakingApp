package com.legalimpurity.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.adapters.PortraitRecipeDetailPagerAdapter;
import com.legalimpurity.bakingapp.objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class PortraitRecipeDetailActivity extends AppCompatActivity {
    
    public static final String ARG_RECIPE_OBJ = "ARG_RECIPE_OBJ";
    public static final String ARG_POS = "ARG_POS";

    private PortraitRecipeDetailPagerAdapter mSectionsPagerAdapter;

    private int pos;
    private Recipe recipe;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getData(this,savedInstanceState);
        setAdapter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_RECIPE_OBJ,recipe);
        outState.putInt(ARG_POS,pos);
    }


    private void getData(AppCompatActivity act, Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_RECIPE_OBJ)) {
                recipe = (Recipe) getIntent().getExtras().getParcelable(ARG_RECIPE_OBJ);
                pos = (int) getIntent().getExtras().getInt(ARG_POS,0);
            }
        }
        else if(getIntent() != null && getIntent().getExtras() != null) {
            recipe = (Recipe) getIntent().getExtras().getParcelable(ARG_RECIPE_OBJ);
            pos = getIntent().getIntExtra(ARG_POS,0);
        }
        else
            NavUtils.navigateUpFromSameTask(act);
    }

    private void setAdapter(AppCompatActivity act)
    {
        mSectionsPagerAdapter = new PortraitRecipeDetailPagerAdapter(act,recipe,pos);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);
        tabs.setupWithViewPager(mViewPager);
    }
}
