package com.example.myapplication.ui.Tips;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Tips.Tip;

import java.util.List;

public class TipViewHolder extends RecyclerView.ViewHolder {

    TextView body;
    List<Tip> data;

    public TipViewHolder(@NonNull View itemView, List<Tip> data) {
        super(itemView);
        this.data = data;
        body = itemView.findViewById(R.id.tip_item_body);
    }

    public void bind(Tip tip, int pos){
        body.setText(tip.getAdvice());
    }
}