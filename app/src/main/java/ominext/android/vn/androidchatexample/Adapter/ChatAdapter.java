package ominext.android.vn.androidchatexample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Entities.ChatMessage;
import ominext.android.vn.androidchatexample.R;

/**
 * Created by MyPC on 18/07/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int CHAT_START = 2;
    public static final int CHAT_END = 1;
    private ArrayList<ChatMessage> chats;
    private String mID;

    public ChatAdapter(ArrayList<ChatMessage> chats, String mID) {
        this.chats = chats;
        this.mID = mID;
    }

    @Override
    public int getItemViewType(int position) {

        if (chats.get(position).getIdChat().equals(mID)) {
            return CHAT_END;

        } else return CHAT_START;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == CHAT_END) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_end, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_start, parent, false);

        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chat = chats.get(position);
        holder.tvUser.setText(chat.getSender().toString());
        holder.tvChatContent.setText(chat.getMsg().toString());
        holder.time.setText(chat.getTimeChat().toString());
        holder.time.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser;
        TextView tvChatContent;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUser = (TextView) itemView.findViewById(R.id.tv_email_user);
            tvChatContent = (TextView) itemView.findViewById(R.id.tv_chat_content);
            time = (TextView) itemView.findViewById(R.id.tv_time_chat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}

