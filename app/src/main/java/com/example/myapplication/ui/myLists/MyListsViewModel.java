package com.example.myapplication.ui.myLists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.ListItem;

import java.util.LinkedList;
import java.util.List;

public class MyListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    static List<ListItem> data = new LinkedList<>();

    public MyListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my lists fragment");

        for(int i=0;i<20;i++){
            addList(new ListItem("name " + i));
        }
    }
    public static List<ListItem> getAllList(){
        return data;
    }

    public void addList(ListItem l){
        data.add(l);
    }
    public LiveData<String> getText() {
        return mText;
    }
}