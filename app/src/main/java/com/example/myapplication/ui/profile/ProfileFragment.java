package com.example.myapplication.ui.profile;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private boolean textViewsVisible = true;
    private boolean editTextsVisible = false;
    private FloatingActionButton editBtn;
    private FloatingActionButton saveBtn;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private String userUID;
    private ImageView imageIV;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
            imageIV = binding.profileImage;
            progressBar = binding.profileProgressBar;
            Model.instance().LoadImg(userUID, (bitmap) -> {
                bitmapToImg(bitmap, imageIV);
                imageIV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "load profile:success2");
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

            nameTV.setText(currentUser.getDisplayName());
            phoneTV.setText(currentUser.getPhone());
            emailTV.setText(currentUser.getEmail());
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

        if (currentUser!= null) {
            updateCurrenUser(newName, newPhone, newEmail);
            Model.instance().updateUserProfileData(userUID, newName, newPhone, newEmail);
            Toast.makeText(getActivity(),"Changes Saved",Toast.LENGTH_SHORT).show();
        }
        toggleEditMode();
    }

    private void updateCurrenUser(String newName, String newPhone, String newEmail) {
        currentUser.setEmail(newEmail);
        currentUser.setPhone(newPhone);
        currentUser.setDisplayName(newName);
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
            Model.instance().uploadImg(userUID, selectedImageBitmap, (bitmap) -> {
                ImageView profileImageIV = binding.profileImage;
                bitmapToImg(bitmap, profileImageIV);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}