package com.example.myapplication.ui.newList;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNewListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NewListFragment extends Fragment {

    private FragmentNewListBinding binding;
    private View root;
    private List<String> listItem = new ArrayList<String>();


    private NewListViewModel mViewModel;

    public static NewListFragment newInstance() {
        return new NewListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentNewListBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        return inflater.inflate(R.layout.fragment_new_list, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewListViewModel.class);
        // TODO: Use the ViewModel

        Button addButtonList = getView().findViewById(R.id.addButton);
        addButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "on click!!!", Toast.LENGTH_SHORT).show();
            }
            String list_name = getView().findViewById(R.id.list_name).toString();
            Boolean includeMap =  getView().findViewById(R.id.include_map).isPressed();

            ///`todo :::: send the info to DB
        });

        ImageView addButtonItem = getView().findViewById(R.id.B_add_item);
        addButtonItem.setOnClickListener(new View.OnClickListener() {
            EditText ET =  (EditText)getView().findViewById(R.id.ET_add_item);
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),ET.getText().toString(), Toast.LENGTH_SHORT).show();
                listItem.add(ET.getText().toString());
                ET.setText("");
                ET.setHint("add item");
            }
        });

    }





}
