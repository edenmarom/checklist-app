package com.example.myapplication.ui.myLists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my lists fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}