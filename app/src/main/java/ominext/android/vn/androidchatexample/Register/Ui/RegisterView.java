package ominext.android.vn.androidchatexample.Register.Ui;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface RegisterView {
    void onRegisSusscess();
    boolean onCheckInputData();
    void onRegisFail(String msg);
}
