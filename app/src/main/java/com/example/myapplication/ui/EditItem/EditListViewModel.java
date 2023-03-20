package com.example.myapplication.ui.EditItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;

import java.util.List;


public class EditListViewModel extends ViewModel {
    private LiveData<List<ListItem>> listItems = Model.instance().getAllListItems();

    public LiveData<List<ListItem>> getLists() {
        return listItems;
    }}