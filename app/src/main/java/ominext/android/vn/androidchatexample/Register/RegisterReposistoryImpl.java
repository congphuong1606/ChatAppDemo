package ominext.android.vn.androidchatexample.Register;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import ominext.android.vn.androidchatexample.DoMain.FireBaseHelper;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;
import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public class RegisterReposistoryImpl implements RegisterReposistory {
    private FireBaseHelper helper;
    private DatabaseReference mDatabase;
    private DatabaseReference myUserReference;
    private FirebaseAuth firebaseAuth;



    public RegisterReposistoryImpl() {
        helper = FireBaseHelper.getInstance();
        mDatabase = helper.getDataReference();
        myUserReference = helper.getMyUserReference();
    }

    @Override
    public void SignUp(String email, String pass, final Activity view) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser userFB = task.getResult().getUser();
                    if (userFB != null) {
                        userFB.sendEmailVerification().addOnCompleteListener(view, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    creadUserDatabase();
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
                        postEvent(RegisterEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    private void creadUserDatabase() {
        FirebaseUser curenUser = firebaseAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User(curenUser.getEmail());
        mDatabase.child(FireBaseHelper.USER_PATH)
                .child(curenUser.getUid())
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess (Void aVoid){
                                postEvent(RegisterEvent.onSignUpSuccess);
                         //      registerView.onRegisSusscess();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure (@NonNull Exception e){
                                postEvent(RegisterEvent.onSignUpError, e.getMessage());
                            }
                        });
    }
    private void postEvent(int type) {
        postEvent(type, null);
    }
    private void postEvent(int type, String errorMessage) {
        RegisterEvent event = new RegisterEvent();
        event.setEventType(type);
        if (errorMessage != null) {
            event.setErrorMesage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }


}
