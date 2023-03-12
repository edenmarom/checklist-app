package com.example.myapplication.ui.newList;

import static android.content.ContentValues.TAG;

import static com.example.myapplication.ui.data.LoginRepository.currentUser;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNewListBinding;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewListFragment extends Fragment {

    private FragmentNewListBinding binding;
    private View root;
    private List<String> listItem_ = new ArrayList<String>();
    private ListItem currentList;
    private final String userImagesDBLocation = "list-images/%s.png";

    //DB
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


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
//                Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();


                FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
                String key = database.getReference("lists").push().getKey();
                DatabaseReference lists = database.getReference("lists");

                //add to the list
                String listIdDB = key;

                EditText name_ET = getView().findViewById(R.id.list_name);
                String nameDB = name_ET.getText().toString();
                List<String> listItemDB = listItem_;
                EditText address_ET = getView().findViewById(R.id.list_location);
                String address_s = address_ET.getText().toString().trim();
                Address address = getLatitudeAndLongitudeFromGoogleMapForAddress(address_s);
                List<Double> locationDB = new ArrayList<>();
                locationDB.add(address.getLatitude());
                locationDB.add(address.getLongitude());
                String userIdDB = "5";//currentList.getUserId() TODO:;
                List<String> participantsDB = new ArrayList<>();
                String imgIdlDB = "5";

                lists.child(listIdDB).child("name").setValue(nameDB);
                lists.child(listIdDB).child("items").setValue(listItemDB);
                lists.child(listIdDB).child("location").setValue(locationDB);
                lists.child(listIdDB).child("participants").setValue(participantsDB);
                lists.child(listIdDB).child("image").setValue(imgIdlDB);
                lists.child(listIdDB).child("userID").setValue(userIdDB);

                //back
                getActivity().onBackPressed();

            }
        });

        //add button
        ImageView addButtonItem = getView().findViewById(R.id.B_add_item);
        addButtonItem.setOnClickListener(new View.OnClickListener() {
            EditText ET = (EditText) getView().findViewById(R.id.ET_add_item);

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), ET.getText().toString(), Toast.LENGTH_SHORT).show();
                listItem_.add(ET.getText().toString());
                ET.setText("");
                ET.setHint("add item");
            }
        });

        //finish and add all
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
                            UploadSelectedImg(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void UploadSelectedImg(Bitmap selectedImageBitmap) {
        if (currentUser != null) {//TODO: change img name
            String userUID = currentUser.getUserId();
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
    private void bitmapToImg(Bitmap bm, ImageView imageIV) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        imageIV.setMinimumHeight(dm.heightPixels);
        imageIV.setMinimumWidth(dm.widthPixels);
        imageIV.setImageBitmap(bm);
    }


    public Address getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){

        Geocoder coder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> address;
        try
        {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

//            Log.d(TAG, "Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
            return location;

        }
        catch(Exception e)
        {
            Log.d(TAG, "MY_ERROR : ############Address Not Found");
            return null;
        }
    }
}
