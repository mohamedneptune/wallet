package com.udacity.wallet.data.mapper;


import com.udacity.wallet.data.database.ExpenseEntry;
import com.udacity.wallet.data.model.ExpenseModel;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ExpenseMapper {


    public List<ExpenseModel> convertEntryListToModels(List<ExpenseEntry> expenseEntryList) {
        ModelMapper modelMapper = new ModelMapper();
        List<ExpenseModel> expenseModelList = new ArrayList<>();
        ExpenseModel expenseModel;

        for(int i = 0 ; i < expenseEntryList.size() ; i++){
            expenseModel = modelMapper.map(expenseEntryList.get(i), ExpenseModel.class);
            expenseModelList.add(expenseModel);
        }
        return expenseModelList;
    }

    public ExpenseModel convertEntryToModel(ExpenseEntry expenseEntry) {
        ModelMapper modelMapper = new ModelMapper();
        ExpenseModel expenseModel;
        expenseModel = modelMapper.map(expenseEntry, ExpenseModel.class);
        return expenseModel;
    }

}

