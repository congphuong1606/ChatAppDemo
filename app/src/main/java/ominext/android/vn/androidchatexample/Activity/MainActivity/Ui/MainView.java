package ominext.android.vn.androidchatexample.Activity.MainActivity.Ui;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Entities.User;

/**
 * Created by MyPC on 20/07/2017.
 */

public interface MainView {
    void updateSuccess(String avatar);
    void onSearchSuccess(ArrayList<User> users);
}
