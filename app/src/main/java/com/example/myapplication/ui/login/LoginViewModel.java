package com.example.myapplication.ui.login;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import com.example.myapplication.R;
import com.example.myapplication.model.LoggedInUser;
import com.example.myapplication.model.Model;


public class LoginViewModel extends ViewModel {


    public static LoggedInUser currentUser;

    public LoginViewModel() {
    }

    public void login(View view, Context context, String email, String password) {

        if (!isThereEmptyData(true, email.trim(), password.trim(), "", "")) {
            Model.instance().logIn(email, password, (user) -> {
                if (user != null) {
                    Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_myLists);
                } else
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
            });
        } else Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
    }


    public void register(View view, Context context, String email, String password, String userName, String phone) {
        if (!isThereEmptyData(false, email.trim(), password.trim(), userName.trim(), phone.trim())) {
            Model.instance().register(context, email, password, userName, phone, (user) -> {
                if (user != null) {
                    Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_myLists);
                } else
                    Toast.makeText(context, "Registration Failed!", Toast.LENGTH_SHORT).show();
            });
        } else Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
    }

    public boolean isThereEmptyData(boolean isLogin, String email, String password, String userName, String phone) {
        if (isLogin) return email.trim().isEmpty() || password.isEmpty();
        return email.trim().isEmpty() || password.trim().isEmpty() || userName.trim().isEmpty() || phone.trim().isEmpty();
    }
}