package com.example.myapplication.model;

import android.location.Address;
import android.location.Location;

import java.util.List;

public class ListItem {
    public String name;
    public List<String> listItem;
    public Address location;
    public String userId;
    public List<String> participants ;
    public String imgIdl;


    public ListItem(String name, List<String> listItem, Address location, String userId, List<String> participants, String imgIdl) {
        this.name = name;
        this.listItem = listItem;
        this.location = location;
        this.userId = userId;
        this.participants = participants;
        this.imgIdl = imgIdl;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setListItem(List<String> listItem) {
        this.listItem = listItem;
    }

    public String getName() {
        return name;
    }

    public List<String> getListItem() {
        return listItem;
    }

    public Address getLocation() {
        return location;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getImgIdl() {
        return imgIdl;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setImgIdl(String imgIdl) {
        this.imgIdl = imgIdl;
    }


}



