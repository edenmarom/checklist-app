package com.example.myapplication.ui.sharedLists;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMylistsBinding;
import com.example.myapplication.model.Model;
import com.example.myapplication.ui.myLists.MyListAdapter;
import com.example.myapplication.ui.myLists.MyListsFragmentArgs;
import com.example.myapplication.ui.myLists.MyListsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SharedListsFragment extends Fragment {

    private FragmentMylistsBinding binding;
    FloatingActionButton addNewListBtn;
    NavController navController;
    MyListAdapter adapter;
    SharedListsViewModel viewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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

        binding.RVList.setHasFixedSize(true);
        Fragment fragment = getParentFragment();
        binding.RVList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyListAdapter(getLayoutInflater(), viewModel.getData().getValue(), fragment);
        binding.RVList.setAdapter(adapter);

        viewModel.getData().observe(getViewLifecycleOwner(), adapter::setData);

        return root;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SharedListsViewModel.class);
    }

    public static void reloadData(){
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshMyLists();
    }
}
