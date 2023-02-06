package com.example.myapplication.ui.myLists;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.list.Lists;

public class ListViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView color;
    TextView lastUpdate;
    //Todo: finish

    public ListViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        lastUpdate = itemView.findViewById(R.id.date);
    }

    public void bind(Lists l) {
        title.setText(l.name);
    }


}