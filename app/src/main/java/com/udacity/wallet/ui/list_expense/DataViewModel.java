package com.udacity.wallet.ui.list_expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.udacity.wallet.data.database.AppDatabase;
import com.udacity.wallet.data.database.ExpenseEntry;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<ExpenseEntry>> getExpenseCostCurrentMonth(String mCurrentCategory){
         return mDb.expenseDao().loadExpenseByCategory(mCurrentCategory);
    }

}
