package com.example.myapplication.model;

import static com.example.myapplication.model.LoggedInUser.USER_REF;
import static com.example.myapplication.ui.login.LoginViewModel.currentUser;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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

    public void uploadImg(String userUID, Bitmap selectedImageBitmap, Listener<Bitmap> listener) {
        // update local profile img
        // TODO
        // update profile img on firebase
        firebaseModel.uploadImg(userUID, selectedImageBitmap, listener);
    }

    public void LoadImg(String userUID, Listener<Bitmap> listener) {
        // check if local img is here
        // TODO
        // get profile img from firebase
        firebaseModel.loadImg(userUID, listener);
    }
    public void updateUserProfileData(String userId, String newName, String newPhone, String newEmail) {
        // check if current user exist in local db
        // TODO
        firebaseModel.updateUserProfileData(userId, newName, newPhone, newEmail);
    }

    public void setInRealTimeDatabaseRegister(Context context, String uid, String email, String displayName, String phone) {//TODO CHENA - remove?
        firebaseModel.setInRealTimeDatabaseRegister(context, uid, email, displayName, phone);
    }

    public void logIn(String email, String password, Listener<LoggedInUser> listener) {
        firebaseModel.logIn(email, password, listener);
    }
    public void logOut() {
        firebaseModel.logOut();
    }

    public void register(Context context, String email, String password, String userName, String phone, Listener<LoggedInUser> listener) {
        firebaseModel.register(context, email, password, userName, phone, listener);
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

    public LiveData<List<ListItem>> getAllListItems() {
        if (ListItems == null) {
            executor.execute(() -> {
                ListItem b = new ListItem("AAA", "my-list", null, null, null, null, null);
                localDb.listItemDao().insertAll(b);

                //TODO Check why the list is null

                ListItems = localDb.listItemDao().getAll();
                mainHandler.post(() -> {
                    Log.e("TAG", "done inserting");
                });
            });
            //refreshAllStudents();TODO
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
