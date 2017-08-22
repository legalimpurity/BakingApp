package com.legalimpurity.bakingapp;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legalimpurity.bakingapp.objects.Step;


public class RecipeStepDescriptionFragment extends Fragment {

    public static final String ARG_STEP_OBJ = "ARG_STEP_OBJ";

    private Step step;

    public RecipeStepDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_STEP_OBJ)) {
            step = getArguments().getParcelable(ARG_STEP_OBJ);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(step.getShortDescription());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if (step != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(step.getDescription());
        }

        return rootView;
    }
}
