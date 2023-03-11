package com.example.myapplication.ui.profile;

import static com.example.myapplication.ui.data.LoginRepository.currentUser;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private final String userImagesDBLocation = "user-images/%s.png";
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    StorageReference storageRef = storage.getReference();
    private boolean textViewsVisible = true;
    private boolean editTextsVisible = false;
    FloatingActionButton editBtn;
    FloatingActionButton saveBtn;
    TextView nameTextView;
    TextView phoneTextView;
    TextView emailTextView;
    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    String userUID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTextView = binding.profileName;
        phoneTextView = binding.profilePhone;
        emailTextView = binding.profileEmail;
        nameEditText = binding.profileNameEdit;
        phoneEditText = binding.profilePhoneEdit;
        emailEditText = binding.profileEmailEdit;

        FloatingActionButton editImgBtn = binding.profileEditImgBtn;
        editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        editBtn = binding.profileEditBtn;
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        saveBtn = binding.profileSaveBtn;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditedProfile();
            }
        });

        if (currentUser!= null){
            userUID = currentUser.getUserId();
        }

        LoadProfileImage();
        LoadProfileData();
        return root;
    }

    private void LoadProfileImage() {
        if (currentUser!= null){
            ImageView imageIV = binding.profileImage;
            String path = String.format(userImagesDBLocation, userUID);
            StorageReference imageRef = storageRef.child(path);
            imageRef.getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmapToImg(bm, imageIV);
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

    private void bitmapToImg(Bitmap bm, ImageView imageIV) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        imageIV.setMinimumHeight(dm.heightPixels);
        imageIV.setMinimumWidth(dm.widthPixels);
        imageIV.setImageBitmap(bm);
    }

    private void LoadProfileData() {
        if (currentUser!= null){
            TextView nameTV = binding.profileName;
            TextView phoneTV = binding.profilePhone;
            TextView emailTV = binding.profileEmail;
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

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        ChooseNewProfileImage.launch(i);
    }

    private void toggleEditMode() {
        nameEditText.setText(nameTextView.getText());
        phoneEditText.setText(phoneTextView.getText());
        emailEditText.setText(emailTextView.getText());

        if (textViewsVisible) {
            nameTextView.setVisibility(View.GONE);
            phoneTextView.setVisibility(View.GONE);
            emailTextView.setVisibility(View.GONE);
            nameEditText.setVisibility(View.VISIBLE);
            phoneEditText.setVisibility(View.VISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.GONE);
            saveBtn.setVisibility(View.VISIBLE);
            textViewsVisible = false;
            editTextsVisible = true;
        } else if (editTextsVisible) {
            nameEditText.setVisibility(View.GONE);
            phoneEditText.setVisibility(View.GONE);
            emailEditText.setVisibility(View.GONE);
            nameTextView.setVisibility(View.VISIBLE);
            phoneTextView.setVisibility(View.VISIBLE);
            emailTextView.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.GONE);
            textViewsVisible = true;
            editTextsVisible = false;
        }
    }

    void saveEditedProfile() {
        String newName = nameEditText.getText().toString();
        String newPhone = phoneEditText.getText().toString();
        String newEmail = emailEditText.getText().toString();

        nameTextView.setText(newName);
        phoneTextView.setText(newPhone);
        emailTextView.setText(newEmail);

        Log.d("TAG", "profile edit - new data: ");
        Log.d("TAG", newName);
        Log.d("TAG", newPhone);
        Log.d("TAG", newEmail);

        if (currentUser!= null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
            DatabaseReference users = database.getReference("Users");
            users.child(userUID).child("displayName").setValue(newName);
            users.child(userUID).child("phone").setValue(newPhone);
            users.child(userUID).child("email").setValue(newEmail);

            Toast.makeText(getActivity(),"Changes Saved",Toast.LENGTH_SHORT).show();
        }

        toggleEditMode();
    }


    ActivityResultLauncher<Intent> ChooseNewProfileImage
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().getContentResolver(),
                                    selectedImageUri);
                            UploadSelectedImg(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void UploadSelectedImg(Bitmap selectedImageBitmap) {
        if (currentUser!= null) {
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
                    ImageView profileImageIV = binding.profileImage;
                    bitmapToImg(selectedImageBitmap, profileImageIV);
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