package ominext.android.vn.androidchatexample.Chat.Event;

import ominext.android.vn.androidchatexample.Entities.ChatMessage;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatEvent {
    ChatMessage msg;

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}
