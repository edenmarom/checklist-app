package com.example.myapplication.model;
import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    private static final Model _instance = new Model();
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    private LiveData<List<ListItem>> MyListItems;
    private LiveData<List<SharedListItem>> SharedLists;

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

    final public MutableLiveData<LoadingState> EventMyListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> EventSharedListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

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
    public void updateEditList(String id, String name, String items,String participant ,Listener<Void> listener) {
        firebaseModel.updateList(id, name, items, participant,listener);
        refreshMyLists();
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

    public void getSelectedListData(String id, Listener<ListItem> listener){
        firebaseModel.getSelectedListData(id,listener);
    }

    public LiveData<List<ListItem>> getMyListItems() {
        if(MyListItems == null){
            MyListItems = localDb.listItemDao().getListItemByUserId(currentUser.getUserId());
            refreshMyLists();
        }
        return MyListItems;
    }

    public void refreshMyLists(){
        EventMyListLoadingState.setValue(LoadingState.LOADING);
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
                EventMyListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public LiveData<List<SharedListItem>> getSharedLists() {
        if(SharedLists == null){
            SharedLists = localDb.sharedListItemDao().getMySharedList(currentUser.getEmail());
            refreshMySharedLists();
        }
        return SharedLists;
    }

    public void refreshMySharedLists(){
        EventSharedListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = SharedListItem.getLocalLastUpdate();
        firebaseModel.getMySharedListsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", "Shared lists - firebase return: " + list.size());
                Long time = localLastUpdate;
                for(SharedListItem l:list){
                    localDb.sharedListItemDao().insertAll(l);
                    if (time < l.getLastUpdated()){
                        time = l.getLastUpdated();
                    }
                }
                SharedListItem.setLocalLastUpdate(time);
                EventSharedListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void insertNewList(ListItem l, Listener<String> listener) {
        firebaseModel.insertNewList(l, (listId)->{
            refreshMyLists();
            listener.onComplete(listId);
        });
    }

    public void getAllLocations(Listener<List<List<String>>> callback) {
        firebaseModel.locationChangeListner(callback);
    }

    public void getUsersList(Listener<String[]> callback) {
        firebaseModel.getUsersList(callback);
    }
}
