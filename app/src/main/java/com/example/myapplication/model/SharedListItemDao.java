package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SharedListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SharedListItem... listItems);

    @Query("SELECT * FROM SharedListItem")
    LiveData<List<SharedListItem>> getAllMySharedList();
}

