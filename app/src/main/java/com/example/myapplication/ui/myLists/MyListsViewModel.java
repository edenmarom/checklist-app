package com.example.myapplication.ui.myLists;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.model.ListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyListsViewModel extends ViewModel {

//    private final MutableLiveData<String> mText;
    static List<ListItem> data = new LinkedList<>();


    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public MyListsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is my lists fragment");
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
//        Address location = new Address();
//        location.setLatitude(3.333);
//        location.setLongitude(4.444);
//        for(int i=0;i<20;i++){
//                addList(new ListItem("name", list,null, "2",list,"4"));
//        }
        FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
        DatabaseReference lists = database.getReference("lists");

        List<Object> allListFromDB = new ArrayList<>();
        lists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                //TODO - EDEN TO ADI: i marked the following lines because it crashed the app.
                // Retrieve all data from the dataSnapshot object
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Object value = snapshot.getValue();
//
//                    String nameDB = ((HashMap) value).get("name").toString();
//                    List<Object> listItemDB = Arrays.asList(((HashMap) value).get("items"));
//                    List<String> listItemDB_ = listItemDB.stream()
//                            .map(object -> Objects.toString(object, null))
//                            .collect(Collectors.toList());
//                    List<Object> address =  Arrays.asList(((HashMap) value).get("location"));
//                    Address location = new Address(Locale.getDefault());
//                    location.setLatitude((int)(address.get(0)));
//                    location.setLongitude((int)(address.get(1)));
//                    String userIdDB = ((HashMap) value).get("userID").toString();
//                    List<Object> participantsDB = Arrays.asList(((HashMap) value).get("participants"));
//                    List<String> participantsDB_ = participantsDB.stream()
//                            .map(object -> Objects.toString(object, null))
//                            .collect(Collectors.toList());
//                    String imgIdlDB = ((HashMap) value).get("image").toString();
//
//                    addList(new ListItem(nameDB, listItemDB_,location, userIdDB,participantsDB_,imgIdlDB));
//
//                    allListFromDB.add(value);
//                }

                //TODO - EDEN TO ADI: END
//                Object o = allListFromDB.get(1);
//                Object b = allListFromDB.get(1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }

        });

        Object b = "o;";
    }
    public static List<ListItem> getAllList(){
        return data;
    }

    public void addList(ListItem l){
        data.add(l);
    }
    public LiveData<String> getText() {
        return null;
    }
}