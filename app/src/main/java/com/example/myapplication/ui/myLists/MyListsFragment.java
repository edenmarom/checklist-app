package com.example.myapplication.ui.myLists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMylistsBinding;
import com.example.myapplication.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyListsFragment extends Fragment {

    private FragmentMylistsBinding binding;
    FloatingActionButton addNewListBtn;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyListsViewModel myListsViewModel =
                new ViewModelProvider(this).get(MyListsViewModel.class);

        binding = FragmentMylistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMylists;
        myListsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addNewListBtn = binding.myListsAddNewListBtn;
        navController = NavHostFragment.findNavController(this);

        addNewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_newListFragment);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}