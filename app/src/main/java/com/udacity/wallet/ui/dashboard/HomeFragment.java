package com.udacity.wallet.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.wallet.ExpensesService;
import com.udacity.wallet.R;
import com.udacity.wallet.databinding.HomeFragmentBinding;
import com.udacity.wallet.ui.addexpense.AddExpenseActivity;

import timber.log.Timber;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private HomeFragmentBinding mBinding;

    private int mBudgetValue = 0;
    private SharedPreferences mSharedPreferences;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    private void setupViewModel() {
        // Observe the LiveData object in the ViewModel

        //Observe Expenses Of Current Day
        mViewModel.getExpenseCostCurrentDay().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double expenseCostCurrentDay) {
                Timber.i("expenseCostCurrentDay : " + expenseCostCurrentDay);
                if (expenseCostCurrentDay == null) {
                    expenseCostCurrentDay = 0.0;
                }
                mBinding.expenseCurrentDayCost.setText("" + expenseCostCurrentDay);
            }
        });

        //Observe Expenses Of Current Month
        mViewModel.getExpenseCostCurrentMonth().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double expenseCostCurrentMonth) {
                Timber.i("expenseCostCurrentMonth : " + expenseCostCurrentMonth);
                if (expenseCostCurrentMonth == null) {
                    expenseCostCurrentMonth = 0.0;
                }
                mBinding.expenseCurrentMonthCost.setText("" + expenseCostCurrentMonth);
                int progressForGraph = (int) ((expenseCostCurrentMonth / mBudgetValue) * 75);
                int progress = (int) ((expenseCostCurrentMonth / mBudgetValue) * 100);
                mBinding.customProgressBar.setProgress(progressForGraph);
                mBinding.pointsValueTextView.setText("" + progress);
                //updateWidget;
                ExpensesService.startActionUpdateExpensesWidgets(getContext());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mBudgetValue = mSharedPreferences.getInt("user_budget", 0);
        if (mBudgetValue == 0) {
            mBudgetValue = 1000;
        }
        setupViewModel();
    }
}