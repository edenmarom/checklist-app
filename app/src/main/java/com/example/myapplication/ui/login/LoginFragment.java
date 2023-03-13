package com.example.myapplication.ui.login;

import static com.example.myapplication.model.LoggedInUser.USER_REF;
import static com.example.myapplication.ui.data.LoginRepository.currentUser;
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
import androidx.navigation.Navigation;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.databinding.FragmentLogoutBinding;
import com.example.myapplication.model.LoggedInUser;
import com.example.myapplication.ui.data.LoginRepository;
import com.example.myapplication.ui.logout.LogoutViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    LoginViewModel loginViewModel;
    boolean isLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
            ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        currentUser = new LoggedInUser((HashMap<String, Object>) task.getResult().getValue());
                        Navigation.findNavController(container).navigate(R.id.action_nav_login_to_nav_myLists);
                    } else
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
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
            binding.etPhone.setVisibility(View.GONE);
            binding.btnLogin.setText("LOGIN");
        } else {
            binding.tvSwitchCase.setText("Have user? Login!");
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