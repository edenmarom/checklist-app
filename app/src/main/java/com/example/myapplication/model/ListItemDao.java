package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
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
    LiveData<List<ListItem>> getAll();
//    List<ListItem> getAll();

    @Query("select * from ListItem where listId = :ListId")
    LiveData<ListItem> getListItemById(String ListId);

//    @Query("select location from ListItem")
//    LiveData<List<List<String>>> getLocation();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ListItem... listItems);
//
//    @Delete
//    void delete(ListItem listItem);
}

