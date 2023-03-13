package com.example.myapplication.ui.login;

import static com.example.myapplication.model.LoggedInUser.USER_REF;
import static com.example.myapplication.ui.data.LoginRepository.currentUser;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.model.LoggedInUser;
import com.example.myapplication.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginViewModel extends ViewModel {


    public LoginViewModel() {
    }

    public void login(View view, Context context, String email, String password) {

        if (!isThereEmptyData(true, email.trim(), password.trim(), "", "")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_REF);
                        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    currentUser = new LoggedInUser((HashMap<String, Object>) task.getResult().getValue());
                                    Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_myLists);
                                } else
                                    Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else
                        Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
    }

    public void register(View view, Context context, String email, String password, String userName, String phone) {
        if (!isThereEmptyData(false, email.trim(), password.trim(), userName.trim(), phone.trim())) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Model.instance().setInRealTimeDatabaseRegister(context, FirebaseAuth.getInstance().getCurrentUser().getUid(), email, userName, phone);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName.trim()).build();
                        assert user != null;
                        user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_myLists);
                            } else
                                Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        });
                    } else
                        Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else Toast.makeText(context, "Enter All Data!", Toast.LENGTH_SHORT).show();
    }

    public boolean isThereEmptyData(boolean isLogin, String email, String password, String userName, String phone) {
        if (isLogin) return email.trim().isEmpty() || password.isEmpty();
        return email.trim().isEmpty() || password.trim().isEmpty() || userName.trim().isEmpty() || phone.trim().isEmpty();
    }
}