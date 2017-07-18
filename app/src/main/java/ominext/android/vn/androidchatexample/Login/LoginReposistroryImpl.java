package ominext.android.vn.androidchatexample.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ominext.android.vn.androidchatexample.Lib.Event;
import ominext.android.vn.androidchatexample.Login.Event.LoginEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public class LoginReposistroryImpl implements LoginReposistory {
    Event event;
    private FirebaseAuth firebaseAuth;

    public LoginReposistroryImpl() {
        event = new Event();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void SignIn(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    event.postEvent(LoginEvent.onSignInSuccess);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                event.postEvent(LoginEvent.onSignInFail, e.getMessage());
            }
        });
    }


}
