package com.example.myapplication.ui.myLists;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListItem;

import java.util.List;

public class MyListViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;
    List<ListItem> data;

    public MyListViewHolder(@NonNull View itemView,MyListAdapter.OnItemClickListener listener, List<ListItem> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.title_listItem);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }
    public void bind(ListItem l, int pos) {
        nameTv.setText(l.name);
    }

}
