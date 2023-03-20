package com.example.myapplication.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import com.example.myapplication.model.LoggedInUser;
import com.example.myapplication.model.Model;

public class LoginViewModel extends ViewModel {


    public static LoggedInUser currentUser;

    public LoginViewModel() {
    }

    public void login(View view, Context context, String email, String password) {
        if (!isThereEmptyData(true, email.trim(), password.trim(), "", "")) {
            ProgressDialog progressDialog = loader(context);
            Model.instance().logIn(email, password, (user) -> {
                if (user != null) {
                    passUseNameToMyListFrag(user, view);
                } else
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else {
            Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private ProgressDialog loader(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        return progressDialog;
    }


    public void register(View view, Context context, String email, String password, String userName, String phone) {
        if (!isThereEmptyData(false, email.trim(), password.trim(), userName.trim(), phone.trim())) {
            ProgressDialog progressDialog = loader(context);
            Model.instance().register(email, password, userName, phone, (user) -> {
                if (user != null) {
                    passUseNameToMyListFrag(user, view);
                } else
                    Toast.makeText(context, "Registration Failed!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
    }

    private void passUseNameToMyListFrag(LoggedInUser user, View view) {
        LoginFragmentDirections.ActionNavLoginToNavMyLists action
                = LoginFragmentDirections.actionNavLoginToNavMyLists();
        action.setUserName(user.getDisplayName());
        Navigation.findNavController(view).navigate(action);
    }

    public boolean isThereEmptyData(boolean isLogin, String email, String password, String userName, String phone) {
        if (isLogin) return email.trim().isEmpty() || password.isEmpty();
        return email.trim().isEmpty() || password.trim().isEmpty() || userName.trim().isEmpty() || phone.trim().isEmpty();
    }
}