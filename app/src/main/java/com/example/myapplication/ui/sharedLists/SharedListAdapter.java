package com.example.myapplication.ui.sharedLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.ListItem;
import java.util.List;

public class SharedListAdapter extends RecyclerView.Adapter<SharedListViewHolder> {

    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }


    LayoutInflater inflater;
    List<ListItem> data;
    Fragment fragment;

    public SharedListAdapter(LayoutInflater inflater, List<ListItem> data, Fragment fragment) {
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
    public SharedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new SharedListViewHolder(view, listener, data, fragment);
    }


    @Override
    public void onBindViewHolder(@NonNull SharedListViewHolder holder, int position) {
        ListItem l = data.get(position);
        holder.bind(l, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
