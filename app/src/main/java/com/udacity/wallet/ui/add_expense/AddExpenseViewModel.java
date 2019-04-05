package com.udacity.wallet.ui.add_expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.udacity.wallet.data.database.AppDatabase;
import com.udacity.wallet.data.database.ExpenseEntry;

import java.util.Date;

public class AddExpenseViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public AddExpenseViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void addExpenseToDB(String title, Double cost, String origin, String category,
                                Date expenseDate, Boolean eachMonth, Boolean eachYear) {
        ExpenseEntry taskEntry = new ExpenseEntry(
                (int)(Math.random() * 50000 + 1),
                title,
                cost,
                origin,
                category,
                expenseDate,
                eachMonth,
                eachYear
        );
        mDb.expenseDao().insertTask(taskEntry);
    }

}
