package com.example.myapplication.ui.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textProfile;
//        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        ImageView imageIV = binding.imgTest;

        // on below line we are checking if the image file exist or not.
//        if (imgFile.exists()) {
            // on below line we are creating an image bitmap variable
            // and adding a bitmap to it from image file.
//            Bitmap imgBitmap = BitmapFactory.decodeFile(imageRef.getFile(localFile));

            // on below line we are setting bitmap to our image view.
//            imageIV.setImageBitmap(imgBitmap);


            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("user-images/avatar.png");

            File localFile = null;
            try {
                localFile = File.createTempFile("profile", ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }

        File finalLocalFile = localFile;
        imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("TAG", "load profile:success");
                    Bitmap imgBitmap = BitmapFactory.decodeFile(String.valueOf(imageRef.getFile(finalLocalFile)));
                    imageIV.setImageBitmap(imgBitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("TAG", "load profile:fail");
                }
            });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}