package com.example.myapplication.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.ListItem;


import java.util.List;

@Dao
public interface ListItemDao {
    @Query("select * from ListItem")
    List<ListItem> getAll();


//    @Query("select * from ListItem where userId = :ListId")
//    ListItem getListItemById(String ListId);
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ListItem... listItems);
//
//    @Delete
//    void delete(ListItem listItem);
}

