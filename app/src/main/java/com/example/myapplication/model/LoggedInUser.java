package com.example.myapplication.model;

import androidx.annotation.NonNull;
import java.util.HashMap;

public class LoggedInUser {

    public static final String USER_REF = "Users";
    private String userId;
    private String displayName;
    private String email;
    private String phone;
    private String profilePicUrl;

    public LoggedInUser() {}

    public LoggedInUser(String userId, String displayName, String email, String phone, String profilePicUrl ) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.profilePicUrl = profilePicUrl;
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("displayName",displayName);
        map.put("email",email);
        map.put("phone",phone);
        map.put("profilePicUrl",profilePicUrl);
        return map;
    }

    public LoggedInUser(HashMap<String, Object> map) {
        this.userId = String.valueOf(map.get("userId"));
        this.displayName = String.valueOf(map.get("displayName"));
        this.email = String.valueOf(map.get("email"));
        this.phone = String.valueOf(map.get("phone"));
        this.profilePicUrl = String.valueOf(map.get("profilePicUrl"));
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

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhone() { return phone;}

    public String getEmail() {return email;}

    public String getProfilePicUrl() {
        return profilePicUrl;
    }


}