package com.example.myapplication.ui.myLists;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.list.Lists;

import java.util.LinkedList;

public class MyListsViewModel extends ViewModel {


    LinkedList<Lists> lists = new LinkedList<>();

    public LinkedList<Lists> getList() {
        return lists;
    }
    public void addToList( Lists l){
        lists.add(l);
     }
}