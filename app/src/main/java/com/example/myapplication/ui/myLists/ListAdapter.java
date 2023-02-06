package com.example.myapplication.ui.myLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.LinkedList;
import java.util.List;
import com.example.myapplication.R;
import com.example.myapplication.list.Lists;

import java.util.LinkedList;
public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<Lists> lists;

    public ListAdapter(List<Lists> recipes) {
        this.lists = recipes;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Lists list = lists.get(position);
        holder.bind(list);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
//
//public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
//
//    MyListsFragment.OnItemClickListener listener;
//    LinkedList<Lists> lists;
//
//    public ListAdapter(LinkedList<Lists> lists) {
//        this.listener = listener;
//        this.lists = lists;
//    }
//
//    void setOnItemClickListener(MyListsFragment.OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
//        return new ListViewHolder(view, listener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//        Lists l = lists.get(position);
//        holder.bind(l, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        System.out.println("adi love me" + lists.size());
//        return lists.size();
//    }
//}

