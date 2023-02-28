package com.example.myapplication.model;

import java.util.List;

public class ListItem {
    public String name;
    List<String> listItem;
    Boolean includMap;

    public ListItem(String name, List<String> listItem, Boolean includMap) {
        this.name = name;
        this.listItem = listItem;
        this.includMap = includMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListItem(List<String> listItem) {
        this.listItem = listItem;
    }

    public void setIncludMap(Boolean includMap) {
        this.includMap = includMap;
    }

    public List<String> getListItem() {
        return listItem;
    }

    public Boolean getIncludMap() {
        return includMap;
    }
}
