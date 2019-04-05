package com.udacity.wallet.ui.list_expense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.wallet.R;
import com.udacity.wallet.shared.Constants;

import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    private LayoutInflater mInflater;
    private List<String> mCategories;
    private Context mContext;

    // data is passed into the constructor
    CategoryViewAdapter(Context context, List<String> categories) {
        this.mInflater = LayoutInflater.from(context);
        this.mCategories = categories;
        mContext = context;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.category_title);
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(Constants.CategoryEnum.values()[position].toString());

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mCategories.size();
    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return mCategories.get(id);
    }

    @Override
    public void onClick(View v) {
        Log.i("","");
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


}
