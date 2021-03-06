package ominext.android.vn.androidchatexample.Activity.Register;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ominext.android.vn.androidchatexample.Activity.Register.Event.RegisterEvent;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;
import ominext.android.vn.androidchatexample.Lib.Event;

/**
 * Created by MyPC on 18/07/2017.
 */

public class RegisterReposistoryImpl implements RegisterReposistory {
    private final Event event;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;



    public RegisterReposistoryImpl() {
        event = new Event();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void SignUp(final String name, String email, String pass, final Activity view) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser userFB = task.getResult().getUser();
                    if (userFB != null) {
                        creadUserDatabase(name);
                        userFB.sendEmailVerification().addOnCompleteListener(view, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    event.postEvent(RegisterEvent.onSignUpSuccess);
                                }
                            }
                        });
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        event.postEvent(RegisterEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    private void creadUserDatabase(String name) {
            String id=firebaseAuth.getCurrentUser().getUid();
        String email=firebaseAuth.getCurrentUser().getEmail();
            User currentUser = new User(id,name,email,null,null,true, null);
            mDatabase.child(Instance.USERS_PATH).child(id).setValue(currentUser);

    }

}
