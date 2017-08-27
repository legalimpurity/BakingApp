package com.legalimpurity.bakingapp.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.legalimpurity.bakingapp.objects.Recipe;

/**
 * Created by rajatkhanna on 27/08/17.
 */

public class IngredientsWidgetService  extends IntentService {
    public static final String RECIPE_WIDGET_ACTION_UPDATE = "380cd3c2b2792b840a3f5f95b55dee4a";
    private static final String BUNDLE_RECIPE_WIDGET_DATA = "f6da4fc168faf2dd8f1c06d4890f4c47";

    public IngredientsWidgetService() {
        super("IngredientsWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, IngredientsWidgetService.class);
        intent.setAction(RECIPE_WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_RECIPE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RECIPE_WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Recipe)intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateRecipeWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}