package ominext.android.vn.androidchatexample.Activity.Chat.Ui;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ominext.android.vn.androidchatexample.Adapter.ChatAdapter;
import ominext.android.vn.androidchatexample.Activity.Chat.ChatPresenter;
import ominext.android.vn.androidchatexample.Activity.Chat.ChatPresenterImpl;
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

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        mChats = new ArrayList<>();
        chatPresenter = new ChatPresenterImpl(this);
        setAdapter(view);

        return view;
    }

    private void setAdapter(View v) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        layoutManager = new GridLayoutManager(v.getContext(), 1);
        msgRecycler.setLayoutManager(layoutManager);
        msgRecycler.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(mChats, firebaseAuth.getCurrentUser().getUid());
        msgRecycler.setAdapter(chatAdapter);
        chatPresenter.notyfiAdapter();
    }

    @Override
    public void sendMsg() {

    }

    @Override
    public void msgReceived(ChatMessage msg) {

    }

    @Override
    public void notyfiAdapter(ChatMessage chatMessage) {
        mChats.add(chatMessage);
        msgRecycler.scrollToPosition(mChats.size() - 1);
        chatAdapter.notifyItemInserted(mChats.size() - 1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_send_msg)
    public void onViewClicked() {
        String msg = edtMsg.getText().toString().trim();
        if (!msg.isEmpty()) {
            chatPresenter.sendMessage(msg);
            edtMsg.setText("");
        }
    }
}
