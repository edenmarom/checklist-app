package com.example.myapplication.model;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FirebaseModel{
    FirebaseDatabase db;
    FirebaseStorage storage;
    private final String userImagesDBLocation = "user-images/%s.png";
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    private StorageReference storageRef;


    FirebaseModel(){
        db = FirebaseDatabase.getInstance(dbUrl);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public void uploadImg(String userUID, Bitmap selectedImageBitmap, Model.Listener<Bitmap> callback){
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
    public void loadImg(String userUID, Model.Listener<Bitmap> callback){
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
                    }
                });
    }

    public void registerNewUser(LoggedInUser user, Model.Listener<LoggedInUser> callback){
        // TODO firebase logic here
    }


}
