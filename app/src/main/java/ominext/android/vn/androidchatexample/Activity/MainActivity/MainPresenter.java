package ominext.android.vn.androidchatexample.Activity.MainActivity;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Entities.User;

/**
 * Created by MyPC on 20/07/2017.
 */

public interface MainPresenter {
    void onUpdateMainView();

    void onSearchUser(String s, ArrayList<User> users);
}
