package ominext.android.vn.androidchatexample.Activity.MeActivity.Ui;

/**
 * Created by MyPC on 19/07/2017.
 */

public interface MeActivityView {
    void onSignOutSuccess() ;
    void onUpLoadFail(String msg);
    void onUploadAvatarSuccess(String string);
    void onUploadActivitySuccess(String avatar, String name, String phone,String email);
}
