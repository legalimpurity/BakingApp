package com.legalimpurity.bakingapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.listeners.RecipeIngridentClick;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.legalimpurity.bakingapp.objects.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public class RecipeIngredientsDescriptionAdapter extends RecyclerView.Adapter<RecipeIngredientsDescriptionAdapter.RecipeItemHolder>{

    private Recipe recipeObj;
    private Activity act;
    private RecipeIngridentClick clicker;

    public RecipeIngredientsDescriptionAdapter(Activity act, Recipe recipeObj, RecipeIngridentClick clicker)
    {
        this.act = act;
        this.recipeObj = recipeObj;
        this.clicker = clicker;
    }


    @Override
    public RecipeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(act).inflate(R.layout.recipe_step_description, parent,false);
        return new RecipeItemHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecipeItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(recipeObj == null)
            return 1;
        return recipeObj.getSteps().size() + 1;
    }

    public class RecipeItemHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.root_view) View rootView;
        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.step_number) TextView stepNumber;

        @BindView(R.id.recipe_image_available) ImageView imageAvailable;
        @BindView(R.id.recipe_video_available) ImageView videoAvailable;
        @BindView(R.id.recipe_text_available) ImageView textAvailable;

        private RecipeItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(final int pos)
        {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicker.onRecipeIngridentCardCLick(v,pos);
                }
            });
            if(pos != 0) {
                Step step = recipeObj.getSteps().get(pos - 1);

                recipeName.setText(step.getShortDescription());
                stepNumber.setText(act.getResources().getString(R.string.recipe_steps_prefix,pos));

                imageHelper(step.getDescription(),textAvailable);
                imageHelper(step.getThumbnailURL(),imageAvailable);
                imageHelper(step.getVideoURL(),videoAvailable);
            }
            else {
                recipeName.setText(act.getResources().getString(R.string.recipe_ingredients));
                stepNumber.setText(act.getResources().getString(R.string.recipe_ingredient_prefix,recipeObj.getIngredients().size()));
                imageHelper("",textAvailable);
                imageHelper("",imageAvailable);
                imageHelper("",videoAvailable);
            }
        }

        private void imageHelper(String val, ImageView im)
        {
            if(!TextUtils.isEmpty(val))
                im.setVisibility(View.VISIBLE);
            else
                im.setVisibility(View.GONE);
        }
    }
}
