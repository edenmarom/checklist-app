package com.example.myapplication.ui.EditItem;

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

public class EditListFragment extends Fragment {

    private EditListViewModel mViewModel;
    private FragmentEditListBinding binding;
    private View root;
    private int pos;
    public static EditListFragment newInstance() {
        return new EditListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentEditListBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        pos = Integer.parseInt(getArguments().getString("pos"));

        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditListViewModel.class);


        Button edit_ButtonList = getView().findViewById(R.id.edit_list_EditBtn_editList);
        edit_ButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }

            ;
        });
    }
};