package com.example.myapplication.ui.myLists;

import static android.content.ContentValues.TAG;
import android.location.Address;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.FirebaseModel;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyListsViewModel extends ViewModel {
    static List<ListItem> data = new LinkedList<>();
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    private static LiveData<List<ListItem>> data2 = Model.instance().getAllListItems();

    public MyListsViewModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
        DatabaseReference lists = database.getReference("lists");

        lists.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("TAG", "Error getting list's data", task.getException());
                } else {
                    Log.d("TAG", "load user's data:success " + String.valueOf(task.getResult().getValue()));
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Object value = snapshot.getValue();


                        String nameDB = ((HashMap) value).get("name").toString();
                        List<Object> listItemDB = Arrays.asList(((HashMap) value).get("items"));
                        List<String> listItemDB_ = listItemDB.stream()
                                .map(object -> Objects.toString(object, null))
                                .collect(Collectors.toList());
                        List<Object> address = Arrays.asList(((HashMap) value).get("location"));
                        List<Object> address_ = (List<Object>) address.get(0);
                        List<String> location = new ArrayList<>();
                        location.add(address_.get(0).toString());
                        location.add(address_.get(1).toString());
//                        Address location = new Address(Locale.getDefault());
//                        location.setLatitude((double) (address_.get(0)));
//                        location.setLongitude((double) (address_.get(1)));
                        String userIdDB = ((HashMap) value).get("userID").toString();
                        List<Object> participantsDB = Arrays.asList(((HashMap) value).get("participants"));
                        List<String> participantsDB_ = participantsDB.stream()
                                .map(object -> Objects.toString(object, null))
                                .collect(Collectors.toList());
                        String imgIdlDB = ((HashMap) value).get("image").toString();
                        String listIdlDB = snapshot.getKey().toString();


                        addList(new ListItem(listIdlDB, nameDB, listItemDB_, location, userIdDB, participantsDB_, imgIdlDB));

                    }
                }
            }
        });

    }

    public static List<ListItem> getAllList(){
        return data;
    }

    public static LiveData<List<ListItem>> getdata2(){
        return data2;
    }

    public void addList(ListItem l){
        data.add(l);
    }
}
