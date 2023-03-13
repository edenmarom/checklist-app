package com.example.myapplication.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentLogoutBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseAuth.getInstance().signOut();
        Navigation.findNavController(container).navigate(R.id.action_nav_logout_to_nav_login);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}