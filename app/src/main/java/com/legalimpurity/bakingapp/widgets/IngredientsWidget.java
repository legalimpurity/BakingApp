package com.legalimpurity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.activity.PortraitRecipeDetailActivity;
import com.legalimpurity.bakingapp.activity.RecipeListActivity;
import com.legalimpurity.bakingapp.objects.Ingredient;
import com.legalimpurity.bakingapp.objects.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe,
                                int appWidgetId) {

        Intent goToRecipeListActivity = new Intent(context,RecipeListActivity.class);
        goToRecipeListActivity.putExtra(RecipeListActivity.RECIPE_OBJECT_KEY,recipe);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, goToRecipeListActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.removeAllViews(R.id.widget_ingredients_list);
        views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.appwidget_text,recipe.getName()));
        views.setOnClickPendingIntent(R.id.widget_root_view, pendingIntent);

        for(Ingredient ingredient : recipe.getIngredients()) {
            RemoteViews rvIngredient = new RemoteViews(context.getPackageName(),
                    R.layout.ingredients_widget_item);
            rvIngredient.setTextViewText(R.id.ingredients_content,
                    String.valueOf(ingredient.getQuantity()) +
                            String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
            views.addView(R.id.widget_ingredients_list, rvIngredient);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

