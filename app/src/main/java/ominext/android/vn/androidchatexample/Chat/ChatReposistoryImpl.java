package ominext.android.vn.androidchatexample.Chat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import ominext.android.vn.androidchatexample.Entities.ChatMessage;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatReposistoryImpl implements ChatReposistory {
    DatabaseReference mDatabase;
    private ChildEventListener childEventListener;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child(Instance.USERS_PATH);

    private String name;

    public ChatReposistoryImpl() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void changeConnectionStatus(boolean offline) {

    }

    @Override
    public void sendMessage(String msg) {

        String CurentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = mRef.child(CurentID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.getName().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String sender =name+"";
        String idChat = CurentID + System.currentTimeMillis() + "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        ChatMessage chat = new ChatMessage(idChat, msg, sender, time);
        mDatabase.child(Instance.CHATS_PATH).child(idChat).setValue(chat);
    }

    @Override
    public void setRecipient(String recipient) {

    }
}
