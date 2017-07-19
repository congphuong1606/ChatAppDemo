package ominext.android.vn.androidchatexample.Chat.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ominext.android.vn.androidchatexample.Adapter.ChatAdapter;
import ominext.android.vn.androidchatexample.Chat.ChatPresenter;
import ominext.android.vn.androidchatexample.Chat.ChatPresenterImpl;
import ominext.android.vn.androidchatexample.Entities.ChatMessage;
import ominext.android.vn.androidchatexample.R;

public class ChatFragment extends Fragment implements ChatView {
ChatPresenter chatPresenter;

    @BindView(R.id.msg_recycler)
    RecyclerView msgRecycler;
    @BindView(R.id.edt_msg)
    EditText edtMsg;
    @BindView(R.id.btn_send_msg)
    ImageButton btnSendMsg;
    Unbinder unbinder;
    RecyclerView.LayoutManager layoutManager;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> mChats;
    private String mId;
    private FirebaseUser currentUser;
    private String userID;

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        chatPresenter= new ChatPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        mChats = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();
        layoutManager = new GridLayoutManager(view.getContext(), 1);
        msgRecycler.setLayoutManager(layoutManager);
        msgRecycler.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(mChats, userID);
        msgRecycler.setAdapter(chatAdapter);
        return view;
    }

    @Override
    public void sendMsg() {

    }

    @Override
    public void msgReceived(ChatMessage msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_send_msg)
    public void onViewClicked() {
        String msg=edtMsg.getText().toString().trim();
        if (!msg.isEmpty()) {
            chatPresenter.sendMessage(msg);
            edtMsg.setText("");
        }
    }
}
