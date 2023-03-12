package com.example.myapplication.model;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.myapplication.model.ListItem;
import java.util.List;

@Dao
public interface LoggedInUserDao {

    @Query("select * from LoggedInUser")
    LoggedInUser getLoggedInUser();

}

