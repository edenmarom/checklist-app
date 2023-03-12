package com.example.myapplication.model;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
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

    public void registerNewUser(LoggedInUser user, Model.Listener<LoggedInUser> callback){
        // TODO firebase logic here
    }

    //registerNewUser

//    public void getAllStudentsSince(Long since, Model.Listener<List<Student>> callback){
//        db.collection(Student.COLLECTION)
//                .whereGreaterThanOrEqualTo(Student.LAST_UPDATED, new Timestamp(since,0))
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<Student> list = new LinkedList<>();
//                        if (task.isSuccessful()){
//                            QuerySnapshot jsonsList = task.getResult();
//                            for (DocumentSnapshot json: jsonsList){
//                                Student st = Student.fromJson(json.getData());
//                                list.add(st);
//                            }
//                        }
//                        callback.onComplete(list);
//                    }
//                });
//    }
//
//    public void addStudent(Student st, Model.Listener<Void> listener) {
//        db.collection(Student.COLLECTION).document(st.getId()).set(st.toJson())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        listener.onComplete(null);
//                    }
//                });
//    }
//
//    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
//        StorageReference storageRef = storage.getReference();
//        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = imagesRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                listener.onComplete(null);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        listener.onComplete(uri.toString());
//                    }
//                });
//            }
//        });
//
//    }
}
