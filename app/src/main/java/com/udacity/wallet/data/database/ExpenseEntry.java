package com.udacity.wallet.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "expense")
public class ExpenseEntry implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
    private Double cost;
    private String origin;
    private String category;
    //@TypeConverters({DataConverter.class})
    @TypeConverters({TimestampConverter.class})
    private Date expenseDate;
    private Boolean eachMonth;
    private Boolean eachYear;

    @Ignore
    public ExpenseEntry(String title, Double cost, String origin, String category,
                        Date expenseDate, Boolean eachMonth, Boolean eachYear) {
        this.title = title;
        this.cost = cost;
        this.origin = origin;
        this.category = category;
        this.expenseDate = expenseDate;
        this.eachMonth = eachMonth;
        this.eachYear = eachYear;
    }

    public ExpenseEntry(int id, String title, Double cost, String origin, String category,
                        Date expenseDate, Boolean eachMonth, Boolean eachYear) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.origin = origin;
        this.category = category;
        this.expenseDate = expenseDate;
        this.eachMonth = eachMonth;
        this.eachYear = eachYear;
    }


    public int getId() {
        return id;
    }

    public void setId(int expenseId) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Boolean getEachMonth() {
        return eachMonth;
    }

    public void setEachMonth(Boolean eachMonth) {
        this.eachMonth = eachMonth;
    }

    public Boolean getEachYear() {
        return eachYear;
    }

    public void setEachYear(Boolean eachYear) {
        this.eachYear = eachYear;
    }
}

