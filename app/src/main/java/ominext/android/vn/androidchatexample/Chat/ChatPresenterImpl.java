package ominext.android.vn.androidchatexample.Chat;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.Subscribe;

import ominext.android.vn.androidchatexample.Chat.Event.ChatEvent;
import ominext.android.vn.androidchatexample.Chat.Ui.ChatView;
import ominext.android.vn.androidchatexample.Entities.ChatMessage;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;
import ominext.android.vn.androidchatexample.Lib.EventBus;
import ominext.android.vn.androidchatexample.Lib.GreenRobotEventBus;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatPresenterImpl implements ChatPresenter {
    ChatView chatView;
    EventBus eventBus;
    ChatReposistory chatReposistory;
    DatabaseReference mRefChat = FirebaseDatabase
            .getInstance().getReference()
            .child(Instance.CHATS_PATH);
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

    @Override
    public void notyfiAdapter() {
            mRefChat.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        try {
                            ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                            chatView.notyfiAdapter(chatMessage);

                        } catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}
