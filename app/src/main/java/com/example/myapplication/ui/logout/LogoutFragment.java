package com.example.myapplication.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.databinding.FragmentLogoutBinding;
import com.example.myapplication.model.Model;
import com.example.myapplication.ui.myLists.MyListsFragment;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Model.instance().logOut((Void)->{
            MyListsFragment.reloadData();
            getActivity().finish();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}