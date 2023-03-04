package com.example.myapplication.ui.newList;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNewListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewListFragment extends Fragment {

    private FragmentNewListBinding binding;
    private View root;
    private List<String> listItem = new ArrayList<String>();


    private NewListViewModel mViewModel;

    public static NewListFragment newInstance() {
        return new NewListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentNewListBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        return inflater.inflate(R.layout.fragment_new_list, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewListViewModel.class);
        // TODO: Use the ViewModel

        Button addButtonList = getView().findViewById(R.id.AddButton);
        addButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
            String list_name = getView().findViewById(R.id.list_name).toString();
            Boolean includeMap =  getView().findViewById(R.id.include_map).isPressed();

            ///`todo :::: send the info to DB
        });

        ImageView addButtonItem = getView().findViewById(R.id.B_add_item);
        addButtonItem.setOnClickListener(new View.OnClickListener() {
            EditText ET =  (EditText)getView().findViewById(R.id.ET_add_item);
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),ET.getText().toString(), Toast.LENGTH_SHORT).show();
                listItem.add(ET.getText().toString());
                ET.setText("");
                ET.setHint("add item");
            }
        });

        FloatingActionButton editImgBtn = getView().findViewById(R.id.newListEditBtn);
        editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "in image", Toast.LENGTH_LONG).show();
                imageChooser();
            }
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        ChooseNewProfileImage.launch(i);
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
//                            UploadSelectedImg(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

//    private void UploadSelectedImg(Bitmap selectedImageBitmap) {
//        if (currentUser!= null) {
//            String userUID = currentUser.getUserId();
//            String path = String.format(userImagesDBLocation, userUID);
//            StorageReference imageRef = storageRef.child(path);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] imgData = baos.toByteArray();
//            UploadTask uploadTask = imageRef.putBytes(imgData);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Log.d("TAG", "load profile:fail");
//                    Log.d("TAG", "path: " + path);
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Log.d("TAG", "edit profile:success");
//                    ImageView profileImageIV = binding.profileImage;
//                    bitmapToImg(selectedImageBitmap, profileImageIV);
//                }
//            });
//        }
//    }





}
