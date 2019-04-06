package com.udacity.wallet.ui.listexpense;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.wallet.R;
import com.udacity.wallet.databinding.DataFragmentBinding;
import com.udacity.wallet.shared.Constants;
import com.udacity.wallet.ui.common.RecyclerTouchListner;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends Fragment {

    private DataFragmentBinding mBinding;
    private SharedPreferences.Editor mEditorPreference;
    private Context mContext;

    public static DataFragment newInstance() {
        return new DataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.data_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);
        mContext = getActivity().getApplicationContext();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mEditorPreference = sharedPreferences.edit();

        //Category Spinner
        ArrayList<String> categories = new ArrayList<>();
        for (Enum category : Constants.CategoryEnum.values()) {
            categories.add(category.toString());
        }

        setRecyclerAdapter(categories);

        mBinding.recyclerCategories.addOnItemTouchListener(new RecyclerTouchListner(getActivity(), mBinding.recyclerCategories, new ClickListner(){
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ExpenseListActivity.class);
                mEditorPreference.putInt("category_selected_position", position);
                mEditorPreference.putString("category_selected_name", Constants.CategoryEnum.values()[position].toString());
                mEditorPreference.apply();
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void setRecyclerAdapter(List<String> mCategories) {
        int numberOfColumns = 2;
        mBinding.recyclerCategories.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
        CategoryViewAdapter categoryViewAdapter = new CategoryViewAdapter(mContext, mCategories);
        mBinding.recyclerCategories.setAdapter(categoryViewAdapter);
    }

    public interface ClickListner{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
