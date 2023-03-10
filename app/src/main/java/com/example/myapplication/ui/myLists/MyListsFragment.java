package com.example.myapplication.ui.myLists;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMylistsBinding;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.ui.EditItem.EditListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyListsFragment extends Fragment {

    private FragmentMylistsBinding binding;
    FloatingActionButton addNewListBtn;
    NavController navController;

    List<ListItem> data = MyListsViewModel.getAllList();
//    LiveData<List<ListItem>> data2 = MyListsViewModel.getdata2();





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyListsViewModel myListsViewModel =
                new ViewModelProvider(this).get(MyListsViewModel.class);

        binding = FragmentMylistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        addNewListBtn = binding.myListsAddNewListBtn;
        navController = NavHostFragment.findNavController(this);

        addNewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_newListFragment);
            }
        });

//        RecyclerView list = root.findViewById(R.id.RV_list);
        RecyclerView list = binding.RVList;
        list.setHasFixedSize(true);

        Fragment fragment = getParentFragment();
        list.setLayoutManager(new LinearLayoutManager(getContext()));
//        MyListAdapter adapter = new MyListAdapter(getLayoutInflater(),MyListsViewModel.getdata2().getValue(),fragment);
        MyListAdapter adapter = new MyListAdapter(getLayoutInflater(),data,fragment);
        list.setAdapter(adapter);

//        MyListsViewModel.getdata2().observe(getViewLifecycleOwner(),(data)->{
//            adapter.setData(data);
//        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}