package ominext.android.vn.androidchatexample.Activity.MeActivity;

import android.net.Uri;

/**
 * Created by MyPC on 19/07/2017.
 */

public interface MeActivityPresenter {
    void signOut();
    void uploadUserAvatar(String pathImage);
    void uploadUserAvatar(Uri uri);
    void updateUser(String downloadUrl);
    void onUpdateUserName(String userName);
    void onUpdateNumberName(String phoneNumber);
    void onUpdateActivity();
    void uploadUserAvatar(byte[] b);
}
