package com.legalimpurity.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.legalimpurity.bakingapp.adapters.RecipeCardsAdapter;
import com.legalimpurity.bakingapp.listeners.RecipeCardClick;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.legalimpurity.bakingapp.retrofitInterfaces.BakingClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectARecipe extends AppCompatActivity {

    @BindView(R.id.recipe_cards)
    RecyclerView recipe_cards;

    private RecipeCardsAdapter mAdapter;
    private ArrayList<Recipe> recipeList;

    private static final String SAVED_INSTANCE_DATA = "SAVED_INSTANCE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_arecipe);
        ButterKnife.bind(this);
        setAdapter(this);
        checkForSavedInstanceState(savedInstanceState,this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_INSTANCE_DATA, recipeList);
    }

    private void checkForSavedInstanceState(Bundle savedInstanceState, AppCompatActivity act)
    {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SAVED_INSTANCE_DATA)) {
                recipeList = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_DATA);
                mAdapter.setMoviesData(recipeList);
            }
        }
        else
            hitNetwork(act);
    }

    private void setAdapter(final Activity act)
    {
        int numberOfColumns = 2;
        if(act.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            numberOfColumns = act.getResources().getInteger(R.integer.no_of_columns_portrait);
        }
        else
        {
            numberOfColumns = act.getResources().getInteger(R.integer.no_of_columns_landscape);
        }

        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(act,numberOfColumns);
        recipe_cards.setLayoutManager(moviesLayoutManager);

        recipe_cards.setHasFixedSize(true);

        mAdapter = new RecipeCardsAdapter(act, new RecipeCardClick() {
            @Override
            public void onRecipeCardCLick(Recipe recipe) {
                Intent goToRecipeListActivity = new Intent(act,RecipeListActivity.class);
                goToRecipeListActivity.putExtra(RecipeListActivity.RECIPE_OBJECT_KEY,recipe);
                act.startActivity(goToRecipeListActivity);
            }
        });

        recipe_cards.setAdapter(mAdapter);

    }

    private void hitNetwork (AppCompatActivity act)
    {
        String API_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        BakingClient client =  retrofit.create(BakingClient.class);

        Call<ArrayList<Recipe>> call =
                client.recipesForBaking("2017","May");

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                if(response.isSuccessful()) {
                    recipeList = response.body();
                    mAdapter.setMoviesData(recipeList);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
