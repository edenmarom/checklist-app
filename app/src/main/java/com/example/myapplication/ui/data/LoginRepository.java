package com.example.myapplication.ui.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;
    private FirebaseAuth mAuth;
    private final String dbUrl = "https://checklist-f8ac0-default-rtdb.europe-west1.firebasedatabase.app";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public static LoggedInUser currentUser;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
        mAuth = FirebaseAuth.getInstance();
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {

        //TODO
        // https://firebase.google.com/docs/auth/android/start - sign in / login  examples
        // user:
        // email: edenmarom@gmail.com
        // pass: 123456

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
//                        FragmentFactory.startAdminFragment((MainActivity) getActivity());
                          Log.d("TAG", "signInWithEmail:success");
                          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                          if (user != null) {
                              String email = user.getEmail();
                              String uid = user.getUid();
                              Log.d("TAG", email);
                              Log.d("TAG", uid);

                              // Write a a user to the database example
                              FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
                              DatabaseReference users = database.getReference("Users");
                              currentUser =
                                      new LoggedInUser(
                                              uid,
                                              "Eden Marom", email,"054-3399867",null);
                              users.child(uid).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void> (){
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          Log.d("TAG", "write to firebase:success");
                                      } else {
                                          Log.d("TAG", "write to firebase:failed");
                                      }
                                  }
                              } );

                          }
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "signInWithEmail:failure");
//                        Toast.makeText( getContext(), e.getMessage(), Toast.LENGTH_LONG ).show();
                    }
                } );

        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
