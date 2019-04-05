package com.udacity.wallet.ui.statistic_expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.wallet.data.database.AppDatabase;

public class StatViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public StatViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(application.getApplicationContext());
    }


    public LiveData<Double> loadAllExpenseCost(){
        return mDb.expenseDao().loadAllExpenseCost();
    }

    public float getExpenseCostByCategory(String category) {
        return (float)mDb.expenseDao().loadCostExpensesOfCurrentMonthByCategory(category);

    }


}
