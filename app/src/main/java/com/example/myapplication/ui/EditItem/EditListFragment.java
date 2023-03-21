package com.example.myapplication.ui.EditItem;
import static com.example.myapplication.ui.login.LoginViewModel.currentUser;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditListBinding;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.Model;
import com.squareup.picasso.Picasso;

public class EditListFragment extends Fragment {

    private ListItem list_;
    private FragmentEditListBinding binding;
    private View root;
    private String id;
    private ActivityResultLauncher<String> galleryLauncher;

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

        Model.instance().getSelectedListData(id, (listItem) -> {
            list_ = listItem;
            binding.listNameEditList.setText(listItem.getName());
            binding.listItems.setText(listItem.getListItem().toString().replace("]","").replace("[",""));

            String url = listItem.getImgUrl();
            if(url != "" && url  != null && url.length() > 5){
                Picasso.get().load(url).placeholder(R.drawable.list).into(binding.editListImage);
            }
        });

        Model.instance().getUsersList((emailList)->{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, emailList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.participantsSpinner.setAdapter(adapter);
        });

        binding.addImgEditList.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        binding.editListEditBtnEditList.setOnClickListener(view1-> {
            String listitems = binding.listItems.getText().toString();
            String listName = binding.listNameEditList.getText().toString();
            String participant = binding.participantsSpinner.getSelectedItem().toString();

            Model.instance().updateEditList(id, listName, listitems, participant, (Void)->{
                Model.instance().refreshMyLists();
                getActivity().onBackPressed();
            });
        });
        return root;
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