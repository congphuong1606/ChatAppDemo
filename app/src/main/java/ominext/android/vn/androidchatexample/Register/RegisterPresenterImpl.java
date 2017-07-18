package ominext.android.vn.androidchatexample.Register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.Lib.EventBus;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;
import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;
import ominext.android.vn.androidchatexample.Register.Ui.RegisterView;

/**
 * Created by MyPC on 18/07/2017.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    EventBus eventBus;
    RegisterView registerView;
    RegisterReposistory registerReposistory;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.eventBus= GreenRobotEventBus.getInstance();

    }


    public void addAuth() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void remoteAuth() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    public void registerNewUser(String email, String password) {
        if (registerView.onCheckInputData()) {
            registerReposistory.SignUp(email, password, (Activity)registerView);
        }
    }
    @Override
    @Subscribe
    public void onEventMainThread(RegisterEvent event) {
        switch (event.getEventType()){
            case RegisterEvent.onSignUpSuccess:
                registerView.onRegisSusscess();
                break;
            case RegisterEvent.onSignUpError:
                registerView.onRegisFail(event.getErrorMesage().toString());
                break;
        }

    }
}
