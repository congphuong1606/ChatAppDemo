package ominext.android.vn.androidchatexample.Register;

import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface RegisterPresenter {
    void registerNewUser(String email, String password);
    void onEventMainThread(RegisterEvent event);
}
