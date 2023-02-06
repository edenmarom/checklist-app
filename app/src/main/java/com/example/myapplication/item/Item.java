package com.example.myapplication.item;

import android.media.Image;

import java.util.Date;

public class Item {
    public String name;
    public String category;
    public String descruption;
    public Date addDate;
    public int quantity;
    public Image image;
//    public location TODO

    public Item(String name, String category, String description, Date addDate, int quantity, Image image) {
        this.name = name;
        this.category = category;
        this.descruption = description;
        this.addDate = addDate;
        this.quantity = quantity;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescruption(String descruption) {
        this.descruption = descruption;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescruption() {
        return descruption;
    }

    public Date getAddDate() {
        return addDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public Image getImage() {
        return image;
    }
}

