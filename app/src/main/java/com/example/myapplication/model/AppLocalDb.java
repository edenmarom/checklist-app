package com.example.myapplication.model;
import static com.example.myapplication.MyApplication.getMyContext;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.myapplication.MyApplication;

@Database(entities = {ListItem.class, SharedListItem.class}, version = 91)
@TypeConverters({Converters.class})
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ListItemDao listItemDao();
    public abstract SharedListItemDao sharedListItemDao();
}

public class AppLocalDb {

    static String DB_NAME = "dbFileName2.db";

    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    static public Void clear() {
        getMyContext().deleteDatabase(DB_NAME);
        return null;
    }

    private AppLocalDb(){}
}

