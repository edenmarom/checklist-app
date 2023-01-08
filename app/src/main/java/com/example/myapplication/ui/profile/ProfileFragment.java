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
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private final String userImagesDBLocation = "user-images/%s.png";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadProfileImage();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}