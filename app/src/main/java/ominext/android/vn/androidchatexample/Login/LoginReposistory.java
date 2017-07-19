package ominext.android.vn.androidchatexample.Login;

import android.app.Activity;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface LoginReposistory {
    void SignIn(final String email, final String pass, final Activity activity);

}