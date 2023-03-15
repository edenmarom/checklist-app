package com.example.myapplication.ui.profile;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.model.Model;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private boolean textViewsVisible = true;
    private boolean editTextsVisible = false;
    private String userUID;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    UploadSelectedImg(result);
                }
            }
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.profileEditImgBtn.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        binding.profileEditBtn.setOnClickListener(view1->{
            toggleEditMode();
        });

        binding.profileSaveBtn.setOnClickListener(view1->{
            saveEditedProfile();
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

            String url = currentUser.getProfilePicUrl();
            if (url  != null && url.length() > 5) {
                  Picasso.get().load(url).placeholder(R.drawable.avatar).into( binding.profileImage);
            } else {
                binding.profileImage.setImageResource(R.drawable.avatar);
            }

            binding.profileImage.setVisibility(View.VISIBLE);
            binding.profileProgressBar.setVisibility(View.GONE);
        }
    }

    private void LoadProfileData() {
        if (currentUser!= null){
            binding.profileName.setText(currentUser.getDisplayName());
            binding.profilePhone.setText(currentUser.getPhone());
            binding.profileEmail.setText(currentUser.getEmail());
        }
    }

    private void toggleEditMode() {
        binding.profileNameEdit.setText(binding.profileName.getText());
        binding.profilePhoneEdit.setText(binding.profilePhone.getText());
        binding.profileEmailEdit.setText(binding.profileEmail.getText());

        if (textViewsVisible) {
            binding.profileName.setVisibility(View.GONE);
            binding.profilePhone.setVisibility(View.GONE);
            binding.profileEmail.setVisibility(View.GONE);
            binding.profileNameEdit.setVisibility(View.VISIBLE);
            binding.profilePhoneEdit.setVisibility(View.VISIBLE);
            binding.profileEmailEdit.setVisibility(View.VISIBLE);
            binding.profileEditBtn.setVisibility(View.GONE);
            binding.profileSaveBtn.setVisibility(View.VISIBLE);
            textViewsVisible = false;
            editTextsVisible = true;
        } else if (editTextsVisible) {
            binding.profileNameEdit.setVisibility(View.GONE);
            binding.profilePhoneEdit.setVisibility(View.GONE);
            binding.profileEmailEdit.setVisibility(View.GONE);
            binding.profileName.setVisibility(View.VISIBLE);
            binding.profilePhone.setVisibility(View.VISIBLE);
            binding.profileEmail.setVisibility(View.VISIBLE);
            binding.profileEditBtn.setVisibility(View.VISIBLE);
            binding.profileSaveBtn.setVisibility(View.GONE);
            textViewsVisible = true;
            editTextsVisible = false;
        }
    }

    void saveEditedProfile() {
        String newName = binding.profileNameEdit.getText().toString();
        String newPhone = binding.profilePhoneEdit.getText().toString();
        String newEmail = binding.profileEmailEdit.getText().toString();

        binding.profileName.setText(newName);
        binding.profilePhone.setText(newPhone);
        binding.profileEmail.setText(newEmail);

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

    private void UploadSelectedImg(Uri result) {
        if (currentUser!= null) {
            binding.profileImage.setImageURI(result);
            binding.profileImage.setDrawingCacheEnabled(true);
            binding.profileImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.profileImage.getDrawable()).getBitmap();
            Model.instance().uploadImage(userUID, bitmap, url->{
                if (url != null){
                    currentUser.setProfilePicUrl(url);
                    Model.instance().updateUserProfileURl(userUID, url);
                } else {
                    Toast.makeText(getActivity(), "something went wrong...", Toast.LENGTH_SHORT).show();
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