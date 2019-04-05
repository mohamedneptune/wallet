package com.udacity.wallet.ui.add_expense;

import android.app.DatePickerDialog;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.wallet.R;
import com.udacity.wallet.databinding.ActivityExpenceBinding;
import com.udacity.wallet.shared.Constants;
import com.udacity.wallet.shared.Globals;
import com.udacity.wallet.ui.ExpenseAppWidget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private ActivityExpenceBinding mBinding;
    private Boolean mSwitchEachMonth = false;
    private Boolean mSwitchEachYear = false;
    private Date mDate;
    private ArrayList<String> mListCategory;
    private String mCategory;
    private int mYear, mMonth, mDay;
    private String mDateString;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle mParams;
    private AddExpenseViewModel mAddExpenseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_expence);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_expence);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddExpenseViewModel = ViewModelProviders.of(this).get(AddExpenseViewModel.class);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mParams = new Bundle();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mBinding.contentExpenseLayout.switchEachMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSwitchEachMonth = true;
                } else {
                    mSwitchEachMonth = false;
                }
            }
        });

        mBinding.contentExpenseLayout.switchEachYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSwitchEachYear = true;
                } else {
                    mSwitchEachYear = false;
                }
            }
        });

        mDate = new Date(System.currentTimeMillis());
        mDateString = mDate.getDate() + " "
                + Globals.getMonth(mDate.getMonth() + 1) + " "
                + (mDate.getYear() + 1900);
        mBinding.contentExpenseLayout.dateTextview.setText(mDateString);


        //Category Spinner
        mListCategory = new ArrayList<>();
        for (Enum category : Constants.CategoryEnum.values()) {
            mListCategory.add(category.toString());
        }
        mBinding.contentExpenseLayout.categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mListCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.contentExpenseLayout.categorySpinner.setAdapter(adapterCategory);

        mParams.putString("full_text", mCategory);

        //Fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpenseToDB();
                mFirebaseAnalytics.logEvent("add_expense", mParams);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();
            }
        });

        mBinding.contentExpenseLayout.dateImageview.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupViewModel();
    }

    private void setupViewModel() {
        // Observe the LiveData object in the ViewModel
        //Observe Expenses Of Current Day
    }


    private void addExpenseToDB() {

        String title = mBinding.contentExpenseLayout.title.getText().toString();
        Double cost = (!mBinding.contentExpenseLayout.cost.getText().toString().equals("") ? Double.parseDouble(mBinding.contentExpenseLayout.cost.getText().toString()) : 0);
        String origin = mBinding.contentExpenseLayout.origin.getText().toString();
        String category = mCategory;
        Date expenseDate = mDate;
        Boolean eachMonth = mSwitchEachMonth;
        Boolean eachYear = mSwitchEachYear;
        mAddExpenseViewModel.addExpenseToDB (title, cost, origin, category, expenseDate, eachMonth, eachYear);
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.category_spinner) {
            mCategory = mListCategory.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    @Override
    public void onClick(View v) {
        if (v == mBinding.contentExpenseLayout.dateImageview) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mDate = new Date(year-1900, monthOfYear, dayOfMonth);
                            mDateString = dayOfMonth + " "
                                    + Globals.getMonth(monthOfYear + 1) + " "
                                    + (year);
                            mBinding.contentExpenseLayout.dateTextview.setText(mDateString);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
