package ominext.android.vn.androidchatexample.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.R;

/**
 * Created by MyPC on 20/07/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final Context context;
    ArrayList<User> users = new ArrayList<>();

    public SearchAdapter(Context context, int layout, ArrayList<User> users) {
        this.users = users;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_searched, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        Glide.with(context).load(user.getAvatar())
                .into(holder.imvAvatarUserSearch);
        holder.tvSearchName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @OnClick({R.id.imv_avatar_user_search, R.id.tv_search_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_avatar_user_search:
                break;
            case R.id.tv_search_name:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_avatar_user_search)
        CircleImageView imvAvatarUserSearch;
        @BindView(R.id.tv_search_name)
        TextView tvSearchName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
