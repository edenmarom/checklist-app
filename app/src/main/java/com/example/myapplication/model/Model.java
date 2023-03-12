package com.example.myapplication.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public interface GetAllListItemsListener {
        void onComplete(List<ListItem> data);
    }
    public void getAllListItems(GetAllListItemsListener callback){
        executor.execute(()->{
            List<ListItem> data = localDb.listItemDao().getAll();
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public interface AddListItemListener {
        void onComplete();
    }
    public void addListItem(ListItem listItem, AddListItemListener listener){
        executor.execute(()->{
            localDb.listItemDao().insertAll(listItem);
            mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }


}
