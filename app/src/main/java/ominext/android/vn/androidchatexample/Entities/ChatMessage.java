package ominext.android.vn.androidchatexample.Entities;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatMessage {
    String idChat;
    String msg;
    String sender;
    String timeChat;

    public ChatMessage(String idChat, String msg, String sender, String timeChat) {
        this.idChat = idChat;
        this.msg = msg;
        this.sender = sender;
        this.timeChat = timeChat;
    }

    public ChatMessage() {
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimeChat() {
        return timeChat;
    }

    public void setTimeChat(String timeChat) {
        this.timeChat = timeChat;
    }
}
