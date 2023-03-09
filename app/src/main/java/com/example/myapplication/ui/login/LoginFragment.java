package com.example.myapplication.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.databinding.FragmentLogoutBinding;
import com.example.myapplication.ui.logout.LogoutViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    LoginViewModel loginViewModel;
    boolean isLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLogin.setOnClickListener(this);
        binding.tvSwitchCase.setOnClickListener(this);

        isLogin = true;
        switchCase();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void switchCase() {
        isLogin = !isLogin;
        if(isLogin) {
            binding.tvSwitchCase.setText("Haven't user? Register!");
            binding.etUserName.setVisibility(View.GONE);
            binding.btnLogin.setText("LOGIN");
        } else {
            binding.tvSwitchCase.setText("Have user? Login!");
            binding.etUserName.setVisibility(View.VISIBLE);
            binding.btnLogin.setText("REGISTER");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if(isLogin) loginViewModel.login(view, getContext(),binding.etEmail.getText().toString().trim(),binding.etPassword.getText().toString().trim());
                else loginViewModel.register(view,getContext(),binding.etEmail.getText().toString().trim(),binding.etPassword.getText().toString().trim(),binding.etUserName.getText().toString().trim());
                break;
            case R.id.tvSwitchCase:
                switchCase();
                break;
        }
    }
}