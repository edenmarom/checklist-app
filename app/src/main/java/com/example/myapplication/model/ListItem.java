package com.example.myapplication.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.ServerValue;

import com.example.myapplication.MyApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class ListItem {
    @PrimaryKey
    @NonNull
    String listId;
    String name;
    @ColumnInfo(name = "items")
    List<String> items;
    @ColumnInfo(name = "location")
    List<String> location;
    String userId;
    List<String> participants;
    String imgUrl;
    Long lastUpdated;


    public ListItem() {
    }

    public ListItem(String listId, String name, List<String> listItem, List<String> location, String userId, List<String> participants, String imgUrl) {
        this.listId = listId;
        this.name = name;
        this.items = listItem;
        this.location = location;
        this.userId = userId;
        this.participants = participants;
        this.imgUrl = imgUrl;
    }

    static final String LIST_ID = "listId";
    static final String NAME = "name";
    static final String ITEMS = "items";
    static final String LOCATION = "location";
    static final String USER_ID = "userId";
    static final String PARTICIPANTS = "participants";//TODO add this field to firebase
    static final String IMG_URL = "imgUrl";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "list_local_last_update";

    public static ListItem fromJson(Map<String, Object> json) {
        String id = (String) json.get(LIST_ID);
        String name = (String) json.get(NAME);
        String items = convertObjectToString(json.get(ITEMS));
        String location = convertObjectToString(json.get(LOCATION));
        String userId = (String) json.get(USER_ID);
        String participants = convertObjectToString(json.get(PARTICIPANTS));
        String imgUrl = (String) json.get(IMG_URL);
        List<String> itemsList = toArray(items);
        List<String> locationsList = toArray(location);
        List<String> participantsList = toArray(participants);

        ListItem l = new ListItem(id, name, itemsList, locationsList, userId, participantsList, imgUrl);
        l.setLastUpdated(new Long(0));

//        try {//TODO : fix import timestamp
//            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
//            l.setLastUpdated(time.getSeconds());
//        } catch (Exception e) {
//
//        }
        return l;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(LIST_ID, getListId());
        json.put(NAME, getName());
        json.put(ITEMS, getListItem());
        json.put(LOCATION, getLocation());
        json.put(USER_ID, getUserId());
        json.put(PARTICIPANTS, getParticipants());
        json.put(IMG_URL, getImgUrl());
        json.put(LAST_UPDATED, "");
        Log.d("TAG", "ServerValue.TIMESTAMP " + ServerValue.TIMESTAMP);
//        json.put(LAST_UPDATED, FieldValue.serverTimestamp()); // TODO fix import FieldValue
        return json;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(@NonNull String listId) {
        this.listId = listId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListItem(List<String> listItem) {
        this.items = listItem;
    }

    public String getName() {
        return name;
    }

    public List<String> getListItem() {
        return items;
    }

    public List<String> getLocation() {
        return location;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED, time);
        editor.commit();
    }

    private static List<String> toArray(String concatenatedStrings) {
        List<String> myStrings = new ArrayList<>();

        for (String s : concatenatedStrings.split(",")) {
            myStrings.add(s);
        }
        ;

        return myStrings;
    }

    public static String fromArray(List<String> strings) {
        String string = "";

        if (strings != null) {
            for (String s : strings) string += (s + ",");
        }

        return string;
    }


    public static String convertObjectToString(Object obj) {
        if (obj != null) {
            List<String> stringList = new ArrayList<>();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(obj);
                    if (fieldValue instanceof Object[]) {
                        Object[] fieldArray = (Object[]) fieldValue;
                        List<Object> fieldList = Arrays.asList(fieldArray);
                        stringList.add(fieldList.toString());
                    } else {
                        stringList.add(String.valueOf(fieldValue));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            String str = stringList.get(0);
            String trimmedString = str.substring(1, str.length() - 1);
            return trimmedString;
        }
        return "";
    }
}


