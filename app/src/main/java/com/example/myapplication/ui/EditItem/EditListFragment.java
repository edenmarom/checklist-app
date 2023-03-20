package com.example.myapplication.ui.EditItem;

import static com.example.myapplication.ui.login.LoginViewModel.currentUser;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditListBinding;
import com.example.myapplication.databinding.FragmentNewListBinding;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;

import java.util.List;

public class EditListFragment extends Fragment {

    private ListItem list_;
    private EditListViewModel mViewModel;
    private FragmentEditListBinding binding;
    private View root;

    private String id;
    private ActivityResultLauncher<String> galleryLauncher;

    public static EditListFragment newInstance() {
        return new EditListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    UploadSelectedImg(result);
                }
            }
        });
        binding = FragmentEditListBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        id = getArguments().getString("id");

//        LiveData<ListItem> liveData = Model.instance().getListItemById(id);
        Model.instance().getSelectedListData(id, (listItem) -> {
            list_ = listItem;
            binding.listNameEditList.setText(listItem.getName());
            binding.listItems.setText(listItem.getListItem().toString());//todo: change view



//            if(recipe.getRecipeImage() != ""){
//                Picasso.get().load(recipe.getRecipeImage()).placeholder(R.drawable.cooking_icon).into(binding.editRecipeFragmentRecipeImg);
//                isImageSelected = true;
//            }
        });

        binding.addImgEditList.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });


        binding.editListEditBtnEditList.setOnClickListener(view1-> {
            Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        });


        return root;//inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditListViewModel.class);

    }

    private void UploadSelectedImg(Uri result) {
        String listId = id;
        if (currentUser!= null) {
            binding.editListImage.setImageURI(result);
            binding.editListImage.setDrawingCacheEnabled(true);
            binding.editListImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.editListImage.getDrawable()).getBitmap();
            Model.instance().uploadImageList(listId, bitmap, url->{
                if (url != null){
                    list_.setImgUrl(url);
                    Model.instance().updateListImg(listId, url);
                } else {
                    Toast.makeText(getActivity(), "something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
};