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
import com.legalimpurity.bakingapp.objects.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public class RecipeCardsAdapter extends RecyclerView.Adapter<RecipeCardsAdapter.RecipeItemHolder>{
    private ArrayList<Recipe> recipeObjs;
    private Activity act;
    private RecipeCardClick clicker;

    public RecipeCardsAdapter(Activity act, RecipeCardClick clicker)
    {
        this.act = act;
        this.clicker = clicker;
    }

    public void setMoviesData(ArrayList<Recipe> recipeObjs)
    {
        this.recipeObjs = recipeObjs;
        notifyDataSetChanged();
    }

    @Override
    public RecipeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(act).inflate(R.layout.root_recipe_list_item, parent,false);
        return new RecipeItemHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecipeItemHolder holder, int position) {
        Recipe mo = recipeObjs.get(position);
        holder.bind(mo);
    }

    @Override
    public int getItemCount() {
        if(recipeObjs == null)
            return 0;
        return recipeObjs.size();
    }

    public class RecipeItemHolder extends RecyclerView.ViewHolder
    {
        private TextView recipeName;
        private TextView servings;
        private ImageView recipeImage;
        private View rootView;
        private RecipeItemHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            servings = (TextView) itemView.findViewById(R.id.servings);
            rootView = (View) itemView.findViewById(R.id.root_view);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }

        void bind(final Recipe recipe)
        {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicker.onRecipeCardCLick(recipe);
                }
            });
            recipeName.setText(recipe.getName());
            servings.setText(act.getResources().getString(R.string.recipe_servings_prefix,recipe.getServings()));
            Picasso.with(act)
                    .load(UrlUtils.BAKING_API_ROOT_URL + UrlUtils.BAKING_API_IMAGE_APPEND + recipe.getImage())
                    .placeholder(R.mipmap.recipe_icon)
                    .error(R.mipmap.recipe_icon)
                    .into(recipeImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }
}
