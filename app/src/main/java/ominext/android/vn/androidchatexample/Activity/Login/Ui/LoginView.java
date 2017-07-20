package ominext.android.vn.androidchatexample.Activity.Login.Ui;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface LoginView {
    void onSignInSuccess();
    void onVerified();
    void onSignInFail(String msg);
    void onViriFail();
    boolean isCheckInputData();
}
