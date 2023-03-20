package com.example.myapplication.ui.EditItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

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

    private EditListViewModel mViewModel;
    private FragmentEditListBinding binding;
    private View root;
    private String id;
    public static EditListFragment newInstance() {
        return new EditListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentEditListBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        id = getArguments().getString("id");//TODO: insert to firebase

//        LiveData<ListItem> liveData = Model.instance().getListItemById(id);
        Model.instance().getSelectedListData(id, (listItem) -> {
            binding.listNameEditList.setText("listItem.getName().toString()");
            binding.listItems.setText(listItem.getListItem().toString());
//            if(recipe.getRecipeImage() != ""){
//                Picasso.get().load(recipe.getRecipeImage()).placeholder(R.drawable.cooking_icon).into(binding.editRecipeFragmentRecipeImg);
//                isImageSelected = true;
//            }
        });
        binding.editListEditBtnEditList.setOnClickListener(view1-> {
            Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        });


        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditListViewModel.class);

    }
};