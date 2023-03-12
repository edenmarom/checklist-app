package com.example.myapplication.model;

import android.location.Address;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class ListItem {
    @PrimaryKey
    @NonNull
    String listId;
    String name;
    @ColumnInfo(name = "items")
    List<String> items;
    @ColumnInfo(name = "location")
    List<String> location;
    String userId;
    List<String> participants ;
    String imgId;

    public ListItem() {
    }

    public ListItem(String listId,String name, List<String> listItem, List<String> location, String userId, List<String> participants, String imgIdl) {
        this.listId = listId;
        this.name = name;
        this.items = listItem;
        this.location = location;
        this.userId = userId;
        this.participants = participants;
        this.imgId = imgIdl;
    }

    public String getListId() {
        return listId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListItem(List<String> listItem) {
        this.items = listItem;
    }

    public String getName() {
        return name;
    }

    public List<String> getListItem() {
        return items;
    }

    public List<String> getLocation() {
        return location;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getImgId() {
        return imgId;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }


}



