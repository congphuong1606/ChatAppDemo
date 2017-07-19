package ominext.android.vn.androidchatexample.Login;

import ominext.android.vn.androidchatexample.Login.Event.LoginEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface LoginPresenter {
    void onSingIn(final String email, final String pass);

    void onCreate();

    void onDestroy();

    void onEventMainThead(LoginEvent event);

    void addAuth();

    void remoteAuth();



}
