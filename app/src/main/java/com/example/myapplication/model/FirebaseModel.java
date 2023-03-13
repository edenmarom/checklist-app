package com.example.myapplication.model;

import static com.example.myapplication.model.LoggedInUser.USER_REF;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;


public class FirebaseModel {
    FirebaseDatabase db;
    FirebaseStorage storage;
    private final String userImagesDBLocation = "user-images/%s.png";
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    private StorageReference storageRef;


    FirebaseModel() {
        db = FirebaseDatabase.getInstance(dbUrl);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public void uploadImg(String userUID, Bitmap selectedImageBitmap, Model.Listener<Bitmap> callback) {
        String path = String.format(userImagesDBLocation, userUID);
        StorageReference imageRef = storageRef.child(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgData = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imgData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("TAG", "load profile:fail");
                Log.d("TAG", "path: " + path);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("TAG", "edit profile:success");
                callback.onComplete(selectedImageBitmap);
            }
        });
    }

    public void setInRealTimeDatabaseRegister(Context context, String uid, String email, String displayName, String phone) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
        ref.child(uid).setValue(new LoggedInUser(uid, email, phone, displayName).toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser = new LoggedInUser(uid, email, phone, displayName);
                } else
                    Toast.makeText(context, "" + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadImg(String userUID, Model.Listener<Bitmap> callback) {
        String path = String.format(userImagesDBLocation, userUID);
        StorageReference imageRef = storageRef.child(path);
        imageRef.getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        callback.onComplete(bm);
                        Log.d("TAG", "load profile:success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("TAG", "load profile:fail");
                        Log.d("TAG", path);
                        callback.onComplete(null);

                    }
                });
    }

    public void logIn(Context context, String email, String password, Model.Listener<LoggedInUser> callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
                    ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                currentUser = new LoggedInUser((HashMap<String, Object>) task.getResult().getValue());
                                callback.onComplete(currentUser);
//
                            } else
                                callback.onComplete(null);
                        }
                    });
                } else
                    callback.onComplete(null);
            }
        });
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void register(Context context, String email, String password, String userName, String phone, Model.Listener<LoggedInUser> callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    setInRealTimeDatabaseRegister(context, userId, email, userName, phone);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName.trim()).build();
                    assert user != null;
                    user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            currentUser = new LoggedInUser(userId, userName, email, phone);
                            callback.onComplete(currentUser);
                        } else
                            callback.onComplete(null);
                    });
                } else
                    callback.onComplete(null);
            }
        });
    }

    public void isUserLoggedIn(Model.Listener<LoggedInUser> callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
            ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        currentUser = new LoggedInUser((HashMap<String, Object>) task.getResult().getValue());
                        callback.onComplete(currentUser);
                    } else
                        callback.onComplete(null);
                }
            });
        }
    }

    public void updateUserProfileData(String userId, String newName, String newPhone, String newEmail) {
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(newEmail);//TODO CHENA - update auth mail

        DatabaseReference users = db.getReference("Users");
        users.child(userId).child("displayName").setValue(newName);
        users.child(userId).child("phone").setValue(newPhone);
        users.child(userId).child("email").setValue(newEmail);
    }

}
