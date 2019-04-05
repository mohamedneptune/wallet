package com.udacity.wallet.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.udacity.wallet.R;
import com.udacity.wallet.shared.Globals;
import com.udacity.wallet.ui.dashboard_expense.HomeFragment;
import com.udacity.wallet.ui.list_expense.DataFragment;
import com.udacity.wallet.ui.profile.ProfileFragment;
import com.udacity.wallet.ui.statistic_expense.StatFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(0, HomeFragment.class);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(1, DataFragment.class);
                    return true;
                case R.id.navigation_stat:
                    setFragment(1, StatFragment.class);
                    return true;
                case R.id.navigation_profile:
                    setFragment(0, ProfileFragment.class);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Globals.setApplicationContext(this);

        setFragment(0, HomeFragment.class);
    }

    public void setFragment(int position, Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment, fragmentClass.getSimpleName());
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.e("setFragment", ex.getMessage());
        }
    }

}