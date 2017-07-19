package ominext.android.vn.androidchatexample.Chat;

import ominext.android.vn.androidchatexample.Chat.Event.ChatEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();
    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
