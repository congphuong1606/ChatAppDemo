package ominext.android.vn.androidchatexample.Activity.Login;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.BaseActivity;
import ominext.android.vn.androidchatexample.Lib.EventBus;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;
import ominext.android.vn.androidchatexample.Activity.Login.Event.LoginEvent;
import ominext.android.vn.androidchatexample.Activity.Login.Ui.LoginView;


/**
 * Created by MyPC on 18/07/2017.
 */

public class LoginPresenterImpl extends BaseActivity implements LoginPresenter {
    EventBus eventBus;
    LoginView loginView;
    LoginReposistory loginReposistory;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public LoginPresenterImpl(final LoginView loginView) {
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.loginReposistory = new LoginReposistroryImpl();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.isEmailVerified()) {
                        hideProgressDialog();
                        loginView.onVerified();

                    } else {
                        firebaseAuth.signOut();
                        hideProgressDialog();
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
            showProgressDialog("Đăng nhập...", (Activity) loginView);
            loginReposistory.SignIn(email, pass, (Activity) loginView);
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThead(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                hideProgressDialog();
                loginView.onSignInSuccess();
                break;
            case LoginEvent.onSignInFail:
                hideProgressDialog();
                loginView.onSignInFail(event.getErrorMesage().toString());
                break;
            case LoginEvent.onFailedToRecoverSession:
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
