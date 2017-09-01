package com.example.ogbeoziomajnr.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ogbeoziomajnr.bakingapp.Activity.IngredientsAndStepsActivity;
import com.example.ogbeoziomajnr.bakingapp.Activity.MainActivity;
import com.example.ogbeoziomajnr.bakingapp.R;

import java.util.logging.Logger;

import timber.log.Timber;

import static android.R.attr.defaultValue;
import static android.provider.Settings.Global.getString;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    private String TAG = this.getClass().getName();

    private static IngredientsAndStepsActivity ingredientsAndStepsActivity = new IngredientsAndStepsActivity();
    private static String ingredientString = null;
    private static String recipeName = null;
    private static Context context1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        context1 = context;

        try {
            CharSequence widgetText = context.getString(R.string.appwidget_text);
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            views.setTextViewText(R.id.widget_title, widgetText);

            readFromSharedPreference();
            if (recipeName != null) {
                views.setTextViewText(R.id.recipe_title, recipeName);
            }
            if (ingredientString != null) {
                views.setTextViewText(R.id.ingredient_string, ingredientString);
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        catch (Exception ex) {
            Timber.e(ex);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            // There may be multiple widgets active, so update all of them
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }

            // start the main Activity when this widget is clicked using pending intents
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            Intent configIntent = new Intent(context, MainActivity.class);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widget_parent, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
        catch (Exception ex)
        {
            Timber.e(ex);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Read all the saved values from shared preferences
     * so that they can be used in updating the widget
     */
    private static void readFromSharedPreference() {
        try {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context1);

        ingredientString = sharedPref.getString(context1
                .getString(R.string.recipe_ingredient_string), ingredientString);

        recipeName = sharedPref.getString(context1
                .getString(R.string.recipe_name), recipeName);
       }
        catch (Exception ex) {
           Timber.e(ex);
        }

    }
}

