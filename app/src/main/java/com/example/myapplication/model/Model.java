package com.example.myapplication.model;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
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
    private LiveData<List<ListItem>> ListItems;
    private List<List<String>> locations;
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    private LiveData<List<ListItem>> MyListItems;

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

//    public void getLocations() {
//        if(ListItems == null){
//            List<List<String>> locations = localDb.listItemDao().getLocation();
//            refreshAllLists();
//        }
//        return ListItems;
//    }


    public LiveData<List<ListItem>> getAllListItems() {
        if(ListItems == null){
            ListItems = localDb.listItemDao().getAll();
            refreshAllLists();
        }
        return ListItems;
    }


//    public LiveData<List<String>> getlocations() {
//        if(locations == null){
//            locations = localDb.listItemDao().getAllLocations();
//        }
//        return locations;
//    }

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
    public void uploadImageList(String uid, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImageList(uid,bitmap,listener);
    }


    public void updateUserProfileURl(String userUID, String url) {
        firebaseModel.updateUserProfileURl(userUID,url);
    }
    public void updateListImg(String userUID, String url) {
        firebaseModel.updateListUrl(userUID,url);
    }

    public void updateUserProfileData(String userId, String newName, String newPhone, String newEmail) {
        firebaseModel.updateUserProfileData(userId, newName, newPhone, newEmail);
    }
    public void updateEditList(String id, String name, String items) {
        firebaseModel.updateList(id, name, items);
        refreshAllLists();//Todo: change refresh all to refresh one
    }
    public void logIn(String email, String password, Listener<LoggedInUser> listener) {
        firebaseModel.logIn(email, password, listener);
    }

    public void logOut(Listener<Void> listener) {
        firebaseModel.logOut(listener);
        AppLocalDb.clear();
    }

    public void register(String email, String password, String userName, String phone, Listener<LoggedInUser> listener) {
        firebaseModel.register(email, password, userName, phone, listener);
    }
    public void isUserLoggedIn(Listener<LoggedInUser> listener) {
        firebaseModel.isUserLoggedIn(listener);
    }

    public LiveData<List<ListItem>> getMyListItems() {
        if(MyListItems == null){
            MyListItems = localDb.listItemDao().getListItemByUserId(currentUser.getUserId());
            refreshMyLists();
        }
        return MyListItems;
    }
    public void getSelectedListData(String id, Listener<ListItem> listener){
        firebaseModel.getSelectedListData(id,listener);
    }




    public void refreshMyLists(){
//        EventStudentsListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = ListItem.getLocalLastUpdate();
        firebaseModel.getMyListsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(ListItem l:list){
                    localDb.listItemDao().insertAll(l);
                    if (time < l.getLastUpdated()){
                        time = l.getLastUpdated();
                    }
                }
                ListItem.setLocalLastUpdate(time);
//                EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void insertNewList(ListItem l, Listener<String> listener) {
        firebaseModel.insertNewList(l, (listId)->{
            refreshAllLists();
            listener.onComplete(listId);
        });
    }


    public void getAllLocations(Model.Listener<List<List<String>>> callback) {
        firebaseModel.locationChangeListner(callback);
    }
    public void refreshAllLists(){
//        EventStudentsListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = ListItem.getLocalLastUpdate();
        firebaseModel.getAllListsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(ListItem l:list){
                    localDb.listItemDao().insertAll(l);
                    if (time < l.getLastUpdated()){
                        time = l.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ListItem.setLocalLastUpdate(time);
//                EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }
}
