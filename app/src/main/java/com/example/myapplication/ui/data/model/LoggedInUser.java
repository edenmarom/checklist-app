package com.example.myapplication.ui.data.model;

import android.media.Image;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String email;
    private String phone;
    private Image profilePic;

    public LoggedInUser(String userId, String displayName, String email, String phone, Image profilePic) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhone() { return phone;}

    public Image getProfilePic() { return profilePic;}

    public String getEmail() {return email;}
}