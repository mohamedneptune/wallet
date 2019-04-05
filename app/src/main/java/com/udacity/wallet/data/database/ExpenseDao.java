package com.udacity.wallet.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface ExpenseDao {


    //fetch Cost Expenses of current Month
    @Query("SELECT sum(cost) " +
            "FROM (SELECT * FROM expense) " +
            "WHERE expenseDate >= DATE('now', '-30 day')")
    LiveData<Double> loadCostExpensesOfCurrentMonth();

    @Query("SELECT sum(cost) " +
            "FROM (SELECT * FROM expense) " +
            "WHERE expenseDate >= DATE('now', '-30 day')")
    Double loadCostExpensesOfCurrentMonth2();


    //fetch Cost Expenses of current Day
    @Query("SELECT sum(cost) " +
            "FROM (SELECT * FROM expense) " +
            "WHERE expenseDate >= DATE('now', '-1 day')")
    LiveData<Double> loadCostExpensesOfCurrentDay();


    //fetch Cost of all Expenses
    @Query("SELECT sum(cost) " +
            "FROM (SELECT * FROM expense)")
    LiveData<Double> loadAllExpenseCost();

    //fetch List Expenses by category
    @Query("SELECT * FROM expense WHERE category =:category")
    LiveData<List<ExpenseEntry>> loadExpenseByCategory(String category);

    //fetch Cost Expenses of current Month By Category
    @Query("SELECT sum(cost) " +
            "FROM (SELECT * FROM expense) " +
            "WHERE expenseDate >= DATE('now', '-30 day')" +
            "AND  category =:category ")
    double loadCostExpensesOfCurrentMonthByCategory(String category);





    // *** Other Query *****

    //fetch Expense by ID
    @Query("SELECT * FROM expense WHERE id =:expenseId")
    ExpenseEntry loadExpensebyId(int expenseId);

    //fetch Expenses of current Month
    @Query("SELECT * FROM expense WHERE expenseDate >= DATE('now', '-30 day')")
    List<ExpenseEntry> loadExpensesOfCurrentMonth();

    //fetch All Expenses
    @Query("SELECT * FROM expense")
    LiveData<List<ExpenseEntry>> loadAllExpense();


    //fetch Expenses from a period of time
    @Query("SELECT * FROM expense WHERE expenseDate between :startDate and :endDate")
    List<ExpenseEntry> loadExpensesByDate(Date startDate, Date endDate);

    @Insert
    void insertTask(ExpenseEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExpense(ExpenseEntry taskEntry);

    @Delete
    void deleteExpense(ExpenseEntry taskEntry);
}
