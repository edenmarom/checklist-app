package com.example.myapplication.ui.myLists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;

import java.util.List;

public class MyListsViewModel extends ViewModel {
    private static LiveData<List<ListItem>> data = Model.instance().getMyListItems();

    public MyListsViewModel() {

    }

    LiveData<List<ListItem>> getData() {
        return data;
    }

    public static final void clear() {
        data = null;
    }
}
