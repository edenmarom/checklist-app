package com.example.myapplication.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    //    private LiveData<List<ListItem>> ListItems;
    private LiveData<List<ListItem>> ListItems;


    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventImgLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public void uploadImage(String uid, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(uid,bitmap,listener);
    }

    public void updateUserProfileURl(String userUID, String url) {
        firebaseModel.updateUserProfileURl(userUID,url);
    }

    public void updateUserProfileData(String userId, String newName, String newPhone, String newEmail) {
        firebaseModel.updateUserProfileData(userId, newName, newPhone, newEmail);
    }

    public void logIn(String email, String password, Listener<LoggedInUser> listener) {
        firebaseModel.logIn(email, password, listener);
    }

    public void logOut() {
        firebaseModel.logOut();
    }

    public void register(String email, String password, String userName, String phone, Listener<LoggedInUser> listener) {
        firebaseModel.register(email, password, userName, phone, listener);
    }
    public void isUserLoggedIn(Listener<LoggedInUser> listener) {
        firebaseModel.isUserLoggedIn(listener);
    }

    public interface GetAllListItemsListener {
        void onComplete(List<ListItem> data);
    }

//    public void getAllListItems(GetAllListItemsListener callback){
//        executor.execute(()->{
//            List<ListItem> data = localDb.listItemDao().getAll();
//            mainHandler.post(()->{
//                callback.onComplete(data);
//            });
//        });
//    }

    public void insert() {
        executor.execute(() -> {
            ListItem b = new ListItem("AAA", "my-list", null, null, null, null, null);
            localDb.listItemDao().insertAll(b);
            mainHandler.post(() -> {
                Log.e("TAG", "done inserting");
            });
        });
    }

    public LiveData<List<ListItem>> getAllListItems() {
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
        }
        if (ListItems == null) {
            executor.execute(() -> {
                ListItems = localDb.listItemDao().getAll();
                mainHandler.post(() -> {
                    Log.e("TAG", "done getting");
                    Log.e("TAG", "ListItems:" + ListItems);
                });
            });
        }
        return ListItems;
    }


//    public interface AddListItemListener {
//        void onComplete();
//    }
//    public void addListItem(ListItem listItem, AddListItemListener listener){
//        executor.execute(()->{
//            localDb.listItemDao().insertAll(listItem);
//            mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
//    }


}
