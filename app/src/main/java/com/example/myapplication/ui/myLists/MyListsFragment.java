package com.example.myapplication.ui.myLists;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import java.util.List;

public class MyListsFragment extends Fragment {

    private FragmentMylistsBinding binding;
    FloatingActionButton addNewListBtn;
    NavController navController;

    List<ListItem> data = MyListsViewModel.getAllList();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyListsViewModel myListsViewModel =
                new ViewModelProvider(this).get(MyListsViewModel.class);

        binding = FragmentMylistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textMylists;
//        myListsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addNewListBtn = binding.myListsAddNewListBtn;
        navController = NavHostFragment.findNavController(this);

        addNewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_newListFragment);
            }
        });

        RecyclerView list = root.findViewById(R.id.RV_list);
        list.setHasFixedSize(true);

        Fragment fragment = getParentFragment();
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyListAdapter adapter = new MyListAdapter(getLayoutInflater(),data,fragment);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);

                //todo::move to edit page

//                Intent i = new Intent(getActivity().this, EditListFragment.class);
//                i.putExtra("key",value);
//                startActivity(i);


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