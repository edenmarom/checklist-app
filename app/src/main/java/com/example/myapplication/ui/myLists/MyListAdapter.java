package com.example.myapplication.ui.myLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListItem;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListViewHolder> {

    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }


    LayoutInflater inflater;
    List<ListItem> data;
    Fragment fragment;

    public MyListAdapter(LayoutInflater inflater, List<ListItem> data, Fragment fragment) {
        this.fragment = fragment;
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<ListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyListViewHolder(view, listener, data, fragment);
    }


    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        ListItem l = data.get(position);
        holder.bind(l, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
