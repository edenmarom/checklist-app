package com.example.myapplication.model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ListItemDao {

    @Query("select * from ListItem where listId = :ListId")
    LiveData<ListItem> getListItemById(String ListId);

//    @Query("select location from ListItem")
//    LiveData<List<List<String>>> getLocation();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ListItem... listItems);
//
//    @Delete
//    void delete(ListItem listItem);

    @Query("select * from ListItem where userId = :id")
    LiveData<List<ListItem>> getListItemByUserId(String id);
}

