package ominext.android.vn.androidchatexample.Activity.Home.Ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ominext.android.vn.androidchatexample.Activity.Home.HomePresenter;
import ominext.android.vn.androidchatexample.Activity.Home.HomePresenterImpl;
import ominext.android.vn.androidchatexample.Adapter.UserAdapter;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.R;

public class HomeFragment extends Fragment implements HomeView {
    HomePresenter homePresenter;
    @BindView(R.id.rcv_user_list)
    RecyclerView rcvUserList;
    @BindView(R.id.fab_new_contact)
    FloatingActionButton fabNewContact;
    Unbinder unbinder;
    RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;
    private ArrayList<User> users=new ArrayList<>();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        setAdpater(view);
        homePresenter=new HomePresenterImpl(this);
        homePresenter.onUpdateUserList(users);
        return view;
    }

    private void setAdpater(View v) {
        layoutManager = new GridLayoutManager(v.getContext(), 1);
        rcvUserList.setLayoutManager(layoutManager);
        rcvUserList.setHasFixedSize(true);
        userAdapter = new UserAdapter(getActivity(), R.layout.item_user_searched, users);
        rcvUserList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRecycleViewSuccsess(ArrayList<User> users) {
        userAdapter.notifyDataSetChanged();
    }
}
