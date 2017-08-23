package com.legalimpurity.bakingapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.Utils.UrlUtils;
import com.legalimpurity.bakingapp.listeners.RecipeCardClick;
import com.legalimpurity.bakingapp.listeners.RecipeIngridentClick;
import com.legalimpurity.bakingapp.objects.Ingredient;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.legalimpurity.bakingapp.objects.Step;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public class RecipeIngridientsDescriptionAdapter extends RecyclerView.Adapter<RecipeIngridientsDescriptionAdapter.RecipeItemHolder>{

    private Recipe recipeObj;
    private Activity act;
    private RecipeIngridentClick clicker;

    public RecipeIngridientsDescriptionAdapter(Activity act, Recipe recipeObj,RecipeIngridentClick clicker)
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

        private ImageView backgroundImage;
        private TextView recipeName;
        private View rootView;
        private RecipeItemHolder(View itemView) {
            super(itemView);
            backgroundImage = (ImageView) itemView.findViewById(R.id.backgroundImage);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            rootView = (View) itemView.findViewById(R.id.root_view);
        }

        void bind(final int pos)
        {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicker.onRecipeIngridentCardCLick(v,pos);
                }
            });
            if(pos != 0)
                recipeName.setText(((Step)recipeObj.getSteps().get(pos -1)).getShortDescription());
            else
                recipeName.setText(act.getResources().getString(R.string.recipe_ingredients));
        }
    }
}
