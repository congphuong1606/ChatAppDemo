package ominext.android.vn.androidchatexample.Register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.BaseActivity;
import ominext.android.vn.androidchatexample.Lib.EventBus;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;
import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;
import ominext.android.vn.androidchatexample.Register.Ui.RegisterView;

/**
 * Created by MyPC on 18/07/2017.
 */

public class RegisterPresenterImpl extends BaseActivity implements RegisterPresenter {
    EventBus eventBus;
    RegisterView registerView;
    RegisterReposistory registerReposistory;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
        registerReposistory = new RegisterReposistoryImpl();

    }


    public void addAuth() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void remoteAuth() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        registerView = null;
        eventBus.unregister(this);
    }

    @Override
    public void registerNewUser(String name,String email, String password, Activity v) {
        if (registerView.onCheckInputData()) {
            showProgressDialog("Vui lòng đợi...", (Activity) registerView);
            registerReposistory.SignUp(name,email, password, v);
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(RegisterEvent event) {
        switch (event.getEventType()) {
            case RegisterEvent.onSignUpSuccess:
                if (registerView != null) {
                    hideProgressDialog();
                    registerView.onRegisSusscess();
                }
                break;
            case RegisterEvent.onSignUpError:
                hideProgressDialog();
                registerView.onRegisFail(event.getErrorMesage().toString());
                break;
        }

    }
}
