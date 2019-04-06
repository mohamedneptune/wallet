package com.udacity.wallet;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.wallet.data.database.AppDatabase;
import com.udacity.wallet.shared.AppExecutors;
import com.udacity.wallet.ui.ExpenseAppWidget;

public class ExpensesService extends IntentService {

    public static final String ACTION_UPDATE_EXPENSE_WIDGETS = "android.appwidget.action.APPWIDGET_UPDATE";
    Double expenseCostCurrentMonth;
    private static final String DATABASE_NAME = "wallet";

    public ExpensesService() {
        super("ExpensesService");
    }

    /**
     * @see IntentService
     */
    public static void startActionUpdateExpensesWidgets(Context context) {
        Intent intent = new Intent(context, ExpensesService.class);
        intent.setAction(ACTION_UPDATE_EXPENSE_WIDGETS);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_EXPENSE_WIDGETS.equals(action)) {
                handleActionUpdateExpensesWidgets();
            }

        }
    }

    /**
     * Handle action UpdateExpensesWidgets in the provided background thread
     */
    private void handleActionUpdateExpensesWidgets() {

        final AppDatabase productDB = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration().build();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                expenseCostCurrentMonth =  productDB.expenseDao().loadCostExpensesOfCurrentMonth2();
            }
        });

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ExpenseAppWidget.class));
        //Now update all widgets
        ExpenseAppWidget.updateExpenses(this, appWidgetManager, ""+expenseCostCurrentMonth, appWidgetIds);
    }
}
