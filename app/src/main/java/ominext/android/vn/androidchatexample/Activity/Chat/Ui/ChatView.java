package ominext.android.vn.androidchatexample.Activity.Chat.Ui;

import ominext.android.vn.androidchatexample.Entities.ChatMessage;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface ChatView {
    void sendMsg();
    void msgReceived(ChatMessage msg);
    void notyfiAdapter(ChatMessage chatMessage);

}
