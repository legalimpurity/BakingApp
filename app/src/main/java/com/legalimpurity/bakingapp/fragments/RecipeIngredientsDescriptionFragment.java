package com.legalimpurity.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.adapters.IngredientsAdapter;
import com.legalimpurity.bakingapp.objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeIngredientsDescriptionFragment extends Fragment{


    @BindView(R.id.ingredients_recycler)
    RecyclerView recycler;

    private IngredientsAdapter mAdapter;

    public static RecipeIngredientsDescriptionFragment newInstance(Recipe stepObj) {
        RecipeIngredientsDescriptionFragment fragment = new RecipeIngredientsDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE_OBJ, stepObj);
        fragment.setArguments(args);
        return fragment;
    }



    public static final String ARG_RECIPE_OBJ = "ARG_RECIPE_OBJ";

    private Recipe recipe;

    public RecipeIngredientsDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_RECIPE_OBJ)) {
            recipe = getArguments().getParcelable(ARG_RECIPE_OBJ);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredients, container, false);
        ButterKnife.bind(this,rootView);
        setupRecyclerView(getActivity(),recycler);
        return rootView;
    }

    private void setupRecyclerView(final FragmentActivity act, @NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        mAdapter = new IngredientsAdapter(act, recipe);
        recyclerView.setAdapter(mAdapter);
    }
}
