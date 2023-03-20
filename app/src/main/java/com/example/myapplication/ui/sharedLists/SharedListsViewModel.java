package com.example.myapplication.ui.sharedLists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;

import java.util.List;

public class SharedListsViewModel extends ViewModel {
    private static LiveData<List<ListItem>> data = Model.instance().getSharedLists();

    public SharedListsViewModel() {}

    LiveData<List<ListItem>> getData() {
        if(data == null){
            return Model.instance().getSharedLists();
        }
        return data;
    }

    public static final void clear() {
        data = null;
    }
}
