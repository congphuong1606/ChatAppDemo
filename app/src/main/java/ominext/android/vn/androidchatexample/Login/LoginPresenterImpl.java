package ominext.android.vn.androidchatexample.Login;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.Login.Event.LoginEvent;
import ominext.android.vn.androidchatexample.Login.Ui.LoginView;


/**
 * Created by MyPC on 18/07/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    LoginView loginView;
    LoginReposistory loginReposistory;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public LoginPresenterImpl(final LoginView loginView) {
        this.loginView = loginView;
        this.loginReposistory = new LoginReposistroryImpl();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.isEmailVerified()) {
                        loginView.onVerified();

                    } else {
                        firebaseAuth.signOut();
                        loginView.onViriFail();

                    }
                } else {

                }
            }
        };
    }

    @Override
    public void onSingIn(String email, String pass) {
        if (loginView.isCheckInputData()) {
            loginReposistory.SignIn(email, pass);
        }
    }

    @Override
    @Subscribe
    public void onEventMainThead(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                loginView.onSignInSuccess();
                break;
            case LoginEvent.onSignInFail:
                loginView.onSignInFail(event.getErrorMesage().toString());
                break;
        }
    }

    @Override
    public void addAuth() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void remoteAuth() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
