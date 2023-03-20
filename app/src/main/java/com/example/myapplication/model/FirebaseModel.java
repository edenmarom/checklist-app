package com.example.myapplication.model;

import static com.example.myapplication.model.LoggedInUser.USER_REF;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.myapplication.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;


public class FirebaseModel {
    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private final String userImagesDBLocation = "user-images/%s.png";
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    private StorageReference storageRef;
    private SharedPreferences preferences;

    FirebaseModel() {
        db = FirebaseDatabase.getInstance(dbUrl);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        preferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getMyContext());
    }

    public void uploadImage(String userUID, Bitmap bitmap, Model.Listener<String> callback) {
        String path = String.format(userImagesDBLocation, userUID);
        StorageReference imageRef = storageRef.child(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgData = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imgData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("TAG", "edit profile pic:fail");
                Log.d("TAG", "path: " + path);
                callback.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG", "edit profile pic:success");
                        callback.onComplete(uri.toString());
                    }
                });
            }
        });
    }

    public void updateUserProfileURl(String userUID, String url) {
        DatabaseReference users = db.getReference("Users");
        users.child(userUID).child("profilePicUrl").setValue(url);
    }

    public void setInRealTimeDatabaseRegister(String uid, String email, String displayName, String phone, Model.Listener<LoggedInUser> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
        ref.child(uid).setValue(new LoggedInUser(uid, displayName, email, phone, "").toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser = new LoggedInUser(uid, displayName, email, phone, "");
                    callback.onComplete(currentUser);
                } else
                    callback.onComplete(null);

            }
        });
    }

    public void logIn(String email, String password, Model.Listener<LoggedInUser> callback) {

        savePasswordToSharedPreferences(password);

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

    public void register(String email, String password, String userName, String phone, Model.Listener<LoggedInUser> callback) {

        savePasswordToSharedPreferences(password);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    setInRealTimeDatabaseRegister(userId, email, userName, phone, (user) -> {
                        if (user == null) {
                            callback.onComplete(null);
                        }
                    });

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName.trim()).build();
                    assert user != null;
                    user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            currentUser = new LoggedInUser(userId, userName, email, phone, "");
                            callback.onComplete(currentUser);
                        } else
                            callback.onComplete(null);
                    });
                } else
                    callback.onComplete(null);
            }
        });
    }

    private void savePasswordToSharedPreferences(String password) {
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("password", password.trim());
        ed.commit();
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
        updateEmailOnFirebaseAuth(newEmail);
        updateProfileDataInRealTimeDB(userId, newName, newPhone, newEmail);
    }

    private void updateProfileDataInRealTimeDB(String userId, String newName, String newPhone, String newEmail) {
        DatabaseReference users = db.getReference("Users");
        users.child(userId).child("displayName").setValue(newName);
        users.child(userId).child("phone").setValue(newPhone);
        users.child(userId).child("email").setValue(newEmail);
    }

    private void updateEmailOnFirebaseAuth(String newEmail) {

        String password = preferences.getString("password", "");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        if (user != null && credential != null) {
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "change email in auth-firebase: success");
                            } else {
                                Log.d("TAG", "change email in auth-firebase: fail");
                            }
                        }
                    });
                }
            });
        } else {
            Log.d("TAG", "change email in auth-firebase: fail");
        }
    }

    public void getAllListsSince(Long since, Model.Listener<List<ListItem>> callback) {
        DatabaseReference lists = db.getReference("lists");
        lists.orderByChild(ListItem.LAST_UPDATED)
                .startAt(since)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ListItem> list = new LinkedList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Map<String, Object> dataMap = (Map<String, Object>) dataSnapshot.getValue();
                            ListItem l = ListItem.fromJson(dataMap);
                            l.setListId(dataSnapshot.getKey());
                            list.add(l);
                        }
                        callback.onComplete(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onComplete(null);
                    }
                });
    }

    public void insertNewList(ListItem l, Model.Listener<Void> callback) {
        DatabaseReference lists = db.getReference("lists");
        lists.push().setValue(l.toJson());
        callback.onComplete(null);
    }
}
