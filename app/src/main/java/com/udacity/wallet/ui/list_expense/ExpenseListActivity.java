package com.udacity.wallet.ui.list_expense;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.udacity.wallet.R;
import com.udacity.wallet.data.database.ExpenseEntry;
import com.udacity.wallet.data.mapper.ExpenseMapper;
import com.udacity.wallet.data.model.ExpenseModel;
import com.udacity.wallet.databinding.ActivityExpenseListBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

public class ExpenseListActivity extends AppCompatActivity implements ExpenseViewAdapter.ItemClickListener{

    private List<ExpenseModel> mExpenseModelList = new LinkedList<>();
    private String mCurrentCategory;
    private SharedPreferences sharedPreferences;
    private ActivityExpenseListBinding mBinding;
    private DataViewModel mDataViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_expense_list);

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        mCurrentCategory = sharedPreferences.getString("category_selected_name", "");

        setTitle(mCurrentCategory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewModel();
    }

    private void setupViewModel() {
        // Observe the LiveData object in the ViewModel

        //Observe Expenses Of Current Day
        mDataViewModel.getExpenseCostCurrentMonth(mCurrentCategory).observe(this, new Observer<List<ExpenseEntry>>() {
            @Override
            public void onChanged(@Nullable List<ExpenseEntry> expenseEntries) {

                if (null != expenseEntries) {
                    Timber.i("nbr expenseEntry : " + expenseEntries.size());
                    //ModelMapper
                    ExpenseMapper expenseMapper = new ExpenseMapper();
                    mExpenseModelList = new ArrayList<>();
                    mExpenseModelList = expenseMapper.convertEntryListToModels(expenseEntries);
                    setRecyclerAdapter(mExpenseModelList);
                }

            }
        });
    }

    private void setRecyclerAdapter(List<ExpenseModel> expenseModelList) {
        int numberOfColumns = 1;
        mBinding.contentExpenseList.recyclerExpense.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        ExpenseViewAdapter expenseViewAdapter = new ExpenseViewAdapter(this, expenseModelList);
        expenseViewAdapter.setClickListener(this);
        mBinding.contentExpenseList.recyclerExpense.setAdapter(expenseViewAdapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Timber.i("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                //onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }
}