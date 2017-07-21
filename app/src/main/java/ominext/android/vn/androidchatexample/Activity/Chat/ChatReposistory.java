package ominext.android.vn.androidchatexample.Activity.Chat;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface ChatReposistory {
    void changeConnectionStatus(boolean offline);
    void sendMessage(String msg);
    void setRecipient(String recipient);
}
