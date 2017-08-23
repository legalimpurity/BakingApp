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
import com.legalimpurity.bakingapp.objects.Ingredient;
import com.legalimpurity.bakingapp.objects.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientItemHolder>{
    private Recipe recipeObj;
    private Activity act;

    public IngredientsAdapter(Activity act, Recipe recipeObj)
    {
        this.act = act;
        this.recipeObj = recipeObj;
    }

    @Override
    public IngredientItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(act).inflate(R.layout.ingredient_list_item, parent,false);
        return new IngredientItemHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(IngredientItemHolder holder, int position) {
        Ingredient mo = recipeObj.getIngredients().get(position);
        holder.bind(mo);
    }

    @Override
    public int getItemCount() {
        if(recipeObj.getIngredients() == null)
            return 0;
        return recipeObj.getIngredients().size();
    }

    public class IngredientItemHolder extends RecyclerView.ViewHolder
    {
        private TextView quantity;
        private TextView name;
        private View rootView;
        private IngredientItemHolder(View itemView) {
            super(itemView);
            quantity = (TextView) itemView.findViewById(R.id.ingredient_quant);
            name = (TextView) itemView.findViewById(R.id.ingredient_name);
            rootView = (View) itemView.findViewById(R.id.root_view);
        }

        void bind(final Ingredient ingredient)
        {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            name.setText(ingredient.getIngredient());
            quantity.setText(ingredient.getQuantity() + ingredient.getMeasure());
        }
    }
}
