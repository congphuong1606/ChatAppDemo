package ominext.android.vn.androidchatexample.Chat;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.Chat.Event.ChatEvent;
import ominext.android.vn.androidchatexample.Chat.Ui.ChatView;
import ominext.android.vn.androidchatexample.Entities.ChatMessage;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Lib.EventBus;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatPresenterImpl implements ChatPresenter {
    ChatView chatView;
    EventBus eventBus;
    ChatReposistory chatReposistory;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        chatReposistory=new ChatReposistoryImpl();
        eventBus = GreenRobotEventBus.getInstance();

    }
    @Override
    public void onPause() {
        chatReposistory.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.chatReposistory.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatReposistory.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (chatView != null) {
            ChatMessage msg = event.getMessage();
            chatView.msgReceived(msg);
        }
    }
}
