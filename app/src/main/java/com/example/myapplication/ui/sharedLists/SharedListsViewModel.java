package com.example.myapplication.ui.sharedLists;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.model.Model;
import com.example.myapplication.model.SharedListItem;

import java.util.List;

public class SharedListsViewModel extends ViewModel {
    private static LiveData<List<SharedListItem>> data = Model.instance().getSharedLists();

    public SharedListsViewModel() {}

    LiveData<List<SharedListItem>> getData() {
        return data;
    }

    public static final void clear() {
        data = null;
    }
}
