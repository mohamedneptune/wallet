package com.udacity.wallet.ui.statistic_expense;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.wallet.R;
import com.udacity.wallet.databinding.StatFragmentBinding;
import com.udacity.wallet.shared.Constants;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;

public class StatFragment extends Fragment {

    private StatViewModel mViewModel;
    private StatFragmentBinding mBinding;
    private PieChartData data;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean hasLabelForSelected = false;

    public static StatFragment newInstance() {
        return new StatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stat_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);

        mBinding.chart.setOnValueTouchListener(new ValueTouchListener());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(StatViewModel.class);

        reset();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewModel();
    }

    private void reset() {
        mBinding.chart.setCircleFillRatio(1.0f);
        hasLabels = true;
        hasLabelsOutside = false;
        hasCenterCircle = true;
        hasCenterText1 = false;
        hasCenterText2 = false;
        hasLabelForSelected = false;
    }

    private void setupViewModel() {
        // Observe the LiveData object in the ViewModel

        //Observe All Expenses
        mViewModel.loadAllExpenseCost().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double globalExpenseCost) {
                generateData();
            }
        });

    }

    private void generateData() {
        //get number of category
        int numValues = Constants.CategoryEnum.values().length;
        float value;
        String category;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            category = Constants.CategoryEnum.values()[i].toString();
            value = mViewModel.getExpenseCostByCategory(category);
            SliceValue sliceValue = new SliceValue(value, ChartUtils.pickColor());
            sliceValue.setLabel(category+": "+value);
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        mBinding.chart.setPieChartData(data);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
        }

    }

}
