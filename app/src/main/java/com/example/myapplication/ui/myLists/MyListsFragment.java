package com.example.myapplication.ui.myLists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMylistsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import com.example.myapplication.list.Lists;

public class MyListsFragment extends Fragment {

    private RecyclerView RV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylists, container, false);

        RV = view.findViewById(R.id.recycelLists);

        return view;
    }
}


//
//    RecyclerView listRV;
//
//    FragmentMylistsBinding binding;
//    FloatingActionButton addNewListBtn;
//    NavController navController;
//    View root;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//       View root = inflater.inflate(R.layout.fragment_mylists, container, false);
//
//       listRV = root.findViewById(R.id.recycelLists);
//
//        listRV.hasFixedSize();
//        listRV.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//
//        MyListsViewModel myListsViewModel =
//                new ViewModelProvider(this).get(MyListsViewModel.class);
//
//
////        MyListsViewModel myListViewModel = new MyListsViewModel();
//
//        myListsViewModel.addToList(new Lists("food","red",false,"icon_1", new Date()));
//        binding = FragmentMylistsBinding.inflate(inflater, container, false);
//
//        root = binding.getRoot();
//
//        addNewListBtn = binding.myListsAddNewListBtn;
//        navController = NavHostFragment.findNavController(this);
//
//        ListAdapter adapter = new ListAdapter(myListsViewModel.getList());
//        listRV.setAdapter(adapter);
//
//
//        addNewListBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(R.id.nav_newListFragment);
//            }
//        });
//
//
//        return root;
//    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int pos);
//    }