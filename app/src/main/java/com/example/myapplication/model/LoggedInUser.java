package com.example.myapplication.model;

import android.media.Image;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;

@Entity
public class LoggedInUser {

    public static final String USER_REF = "Users";
    @PrimaryKey
    @NonNull
    private String userId;
    private String displayName;
    private String email;
    private String phone;
    //private Image profilePic;

    public LoggedInUser() {}

    public LoggedInUser(String userId, String displayName, String email, String phone) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        //this.profilePic = profilePic;
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("displayName",displayName);
        map.put("email",email);
        map.put("phone",phone);
        return map;
    }

    public LoggedInUser(HashMap<String, Object> map) {
        this.userId = String.valueOf(map.get("userId"));
        this.displayName = String.valueOf(map.get("displayName"));
        this.email = String.valueOf(map.get("email"));
        this.phone = String.valueOf(map.get("phone"));
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public void setProfilePic(Image profilePic) {
//        this.profilePic = profilePic;
//    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhone() { return phone;}

//    public Image getProfilePic() { return profilePic;}

    public String getEmail() {return email;}
}