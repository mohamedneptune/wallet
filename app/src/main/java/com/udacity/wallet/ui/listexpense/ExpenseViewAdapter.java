package com.udacity.wallet.ui.listexpense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.wallet.R;
import com.udacity.wallet.data.model.ExpenseModel;
import com.udacity.wallet.shared.Globals;

import java.util.List;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ExpenseModel> mExpenseModelList;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    ExpenseViewAdapter(Context context, List<ExpenseModel> expenseModelList) {
        this.mInflater = LayoutInflater.from(context);
        this.mExpenseModelList = expenseModelList;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView expense_title, expense_cost, expense_date;


        ViewHolder(View itemView) {
            super(itemView);
            expense_title = itemView.findViewById(R.id.expense_title);
            expense_cost = itemView.findViewById(R.id.expense_cost);
            expense_date = itemView.findViewById(R.id.expense_date);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.expense_title.setText(mExpenseModelList.get(position).getTitle());
        holder.expense_cost.setText(mExpenseModelList.get(position).getCost().toString());
        String dateString = mExpenseModelList.get(position).getExpenseDate().getDate() + " "
                + Globals.getMonth(mExpenseModelList.get(position).getExpenseDate().getMonth() + 1) + " "
                + (mExpenseModelList.get(position).getExpenseDate().getYear() + 1900);
        holder.expense_date.setText(dateString);

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mExpenseModelList.size();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
