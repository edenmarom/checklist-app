package com.example.myapplication.model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ListItemDao {

    @Query("select * from ListItem")
    LiveData<List<ListItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ListItem... listItems);

    @Query("select * from ListItem where userId = :id")
    LiveData<List<ListItem>> getListItemByUserId(String id);

}

