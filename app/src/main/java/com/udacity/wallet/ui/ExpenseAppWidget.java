package com.udacity.wallet.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.wallet.ExpensesService;
import com.udacity.wallet.R;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class ExpenseAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String expenseCostCurrentMonth, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.expense_app_widget);
        if(!expenseCostCurrentMonth.equals("null")) {
            views.setTextViewText(R.id.expense_current_month_value, "" + expenseCostCurrentMonth);
        }else{
            views.setTextViewText(R.id.expense_current_month_value, "" + 0);
        }

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0 , intent, 0);
        views.setOnClickPendingIntent(R.id.root_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        ExpensesService.startActionUpdateExpensesWidgets(context);
    }

    public static void updateExpenses(Context context, AppWidgetManager appWidgetManager, String expenseCostCurrentMonth, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, expenseCostCurrentMonth, appWidgetId);
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
}

