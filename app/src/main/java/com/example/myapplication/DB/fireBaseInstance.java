package com.example.myapplication.DB;

import com.google.firebase.database.FirebaseDatabase;

public class fireBaseInstance {
    private static final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    public static FirebaseDatabase instance = FirebaseDatabase.getInstance(dbUrl);

}
