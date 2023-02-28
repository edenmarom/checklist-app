package com.example.myapplication.ui.myLists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.ListItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    static List<ListItem> data = new LinkedList<>();

    public MyListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my lists fragment");
List<String> list = new ArrayList<>();
list.add("item1");
list.add("item2");
        for(int i=0;i<20;i++){
            addList(new ListItem("name " + i,list,true));
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