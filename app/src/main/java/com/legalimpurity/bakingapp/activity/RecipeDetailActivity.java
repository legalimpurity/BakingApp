package com.legalimpurity.bakingapp.activity;

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
import com.legalimpurity.bakingapp.adapters.PotraitRecipeDetailPagerAdapter;
import com.legalimpurity.bakingapp.objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String ARG_RECIPE_OBJ = "ARG_RECIPE_OBJ";
    public static final String ARG_POS = "ARG_POS";

    private PotraitRecipeDetailPagerAdapter mSectionsPagerAdapter;

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

    private void getData(AppCompatActivity act, Bundle savedInstanceState)
    {
        pos = getIntent().getIntExtra(ARG_POS,0);
        recipe = getIntent().getParcelableExtra(ARG_RECIPE_OBJ);
    }

    private void setAdapter(AppCompatActivity act)
    {
        mSectionsPagerAdapter = new PotraitRecipeDetailPagerAdapter(act,recipe,pos);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);
        tabs.setupWithViewPager(mViewPager);
    }
}
