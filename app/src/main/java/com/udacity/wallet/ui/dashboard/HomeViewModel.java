package com.udacity.wallet.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.wallet.data.database.AppDatabase;

public class HomeViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(application.getApplicationContext());
    }


    public LiveData<Double> getExpenseCostCurrentDay() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return mDb.expenseDao().loadCostExpensesOfCurrentDay();

    }

    public LiveData<Double> getExpenseCostCurrentMonth(){
        return mDb.expenseDao().loadCostExpensesOfCurrentMonth();
    }


}
