package com.example.myapplication.ui.Tips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Tips.Tip;

import java.util.List;

public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipViewHolder> {

    LayoutInflater inflater;
    List<Tip> data;

    public void setData(List<Tip> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public TipsRecyclerAdapter(LayoutInflater inflater, List<Tip> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tip_item, parent, false);

        return new TipViewHolder(view, data);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        Tip tip = data.get(position);
        holder.bind(tip, position);
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        return data.size();
    }
}