package com.example.myapplication.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.model.Model;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    LoginViewModel loginViewModel;
    boolean isLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Model.instance().isUserLoggedIn((user)->{
            if(user!=null) {
                Navigation.findNavController(container).navigate(R.id.action_nav_login_to_nav_myLists);
            }else
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        });
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
            binding.tvSwitchCase.setText("New here? Register!");
            binding.etUserName.setVisibility(View.GONE);
            binding.etPhone.setVisibility(View.GONE);
            binding.btnLogin.setText("LOGIN");
        } else {
            binding.tvSwitchCase.setText("Already have an account? Login!");
            binding.etUserName.setVisibility(View.VISIBLE);
            binding.etPhone.setVisibility(View.VISIBLE);
            binding.btnLogin.setText("REGISTER");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if(isLogin) loginViewModel.login(view, getContext(),binding.etEmail.getText().toString().trim(),binding.etPassword.getText().toString().trim());
                else loginViewModel.register(view,getContext(),binding.etEmail.getText().toString().trim(),binding.etPassword.getText().toString().trim(),binding.etUserName.getText().toString().trim(),binding.etPhone.getText().toString().trim());
                break;
            case R.id.tvSwitchCase:
                switchCase();
                break;
        }
    }
}