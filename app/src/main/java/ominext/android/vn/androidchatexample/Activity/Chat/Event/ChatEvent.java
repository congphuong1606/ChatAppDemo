package ominext.android.vn.androidchatexample.Activity.Chat.Event;

import ominext.android.vn.androidchatexample.Entities.ChatMessage;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatEvent {
    public final static int getidSuccsess = 1;
    public final static int getMsgSucseess = 2;
    ChatMessage msg;
    String id;

    public void  ChatEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public ChatEvent(String id) {
        this.id = id;
    }

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}
