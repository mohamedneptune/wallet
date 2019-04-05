package com.udacity.wallet.data.model;


import java.util.Date;

public class ExpenseModel {


    private Integer id;
    private String title;
    private Double cost;
    private String origin;
    private String category;
    private Date expenseDate;
    private Boolean eachMonth;
    private Boolean eachYear;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


