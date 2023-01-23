package com.example.myapplication.ui.profile;

import static com.example.myapplication.ui.data.LoginRepository.currentUser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private final String userImagesDBLocation = "user-images/%s.png";
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadProfileImage();
        LoadProfileData();

        return root;
    }

    private void LoadProfileImage() {
        if (currentUser!= null){
            ImageView imageIV = binding.profileImage;
            StorageReference storageRef = storage.getReference();
            String userUID = currentUser.getUserId();
            String path = String.format(userImagesDBLocation, userUID);
            StorageReference imageRef = storageRef.child(path);
            imageRef.getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            DisplayMetrics dm = new DisplayMetrics();
                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                            imageIV.setMinimumHeight(dm.heightPixels);
                            imageIV.setMinimumWidth(dm.widthPixels);
                            imageIV.setImageBitmap(bm);
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
    }

    private void LoadProfileData() {
        if (currentUser!= null){
            TextView nameTV = binding.profileName;
            TextView phoneTV = binding.profilePhone;
            TextView emailTV = binding.profileEmail;
            String userUID = currentUser.getUserId();
            FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
            DatabaseReference users = database.getReference("Users");
            users.child(userUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("TAG", "Error getting user's data", task.getException());
                    }
                    else {
                        Log.d("TAG", "load user's data:success "+ String.valueOf(task.getResult().getValue()));
                        nameTV.setText(task.getResult().child("displayName").getValue().toString());
                        phoneTV.setText(task.getResult().child("phone").getValue().toString());
                        emailTV.setText(task.getResult().child("email").getValue().toString());
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}