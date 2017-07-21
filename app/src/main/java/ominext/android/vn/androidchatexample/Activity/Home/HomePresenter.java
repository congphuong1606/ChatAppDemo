package ominext.android.vn.androidchatexample.Activity.Home;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Entities.User;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface HomePresenter {
    void onUpdateUserList(ArrayList<User> users);
}
