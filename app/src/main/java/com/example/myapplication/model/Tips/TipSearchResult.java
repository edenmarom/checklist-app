package com.example.myapplication.model.Tips;

import com.example.myapplication.model.Tips.Tip;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TipSearchResult {
    @SerializedName("slips")
    List<Tip> list;

    public List<Tip> getList() {
        return list;
    }

    public void setList(List<Tip> list) {
        this.list = list;
    }
}