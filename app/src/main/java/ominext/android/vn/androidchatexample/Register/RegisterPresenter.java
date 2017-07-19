package ominext.android.vn.androidchatexample.Register;

import android.app.Activity;

import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface RegisterPresenter {
    void onCreate();
    void onDestroy();
    void registerNewUser(String name,String email, String password, Activity activity);
    void onEventMainThread(RegisterEvent event);
}
