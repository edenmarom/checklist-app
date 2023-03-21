package com.example.myapplication.ui.Tips;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentTipsBinding;


public class TipsFragment extends Fragment {

    FragmentTipsBinding binding;
    TipsRecyclerAdapter adapter;
    TipsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTipsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.tipsFragmentRv.setHasFixedSize(true);
        binding.tipsFragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TipsRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.tipsFragmentRv.setAdapter(adapter);

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(TipsFragmentViewModel.class);
    }


}