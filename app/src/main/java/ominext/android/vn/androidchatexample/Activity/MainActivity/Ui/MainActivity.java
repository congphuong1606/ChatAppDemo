package ominext.android.vn.androidchatexample.Activity.MainActivity.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ominext.android.vn.androidchatexample.Activity.MainActivity.MainPresenter;
import ominext.android.vn.androidchatexample.Activity.MainActivity.MainPresenterImpl;
import ominext.android.vn.androidchatexample.Activity.MeActivity.Ui.MeActivity;
import ominext.android.vn.androidchatexample.Adapter.FragmentAdapter;
import ominext.android.vn.androidchatexample.Adapter.UserAdapter;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.R;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.frame_layout)
    ViewPager frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.ic_me)
    CircleImageView icMe;
    @BindView(R.id.btn_creat_new_chat)
    ImageButton btnCreatNewChat;
    @BindView(R.id.rcv_search_view)
    RecyclerView rcvSearchView;
    @BindView(R.id.frame_layout_search)
    FrameLayout frameLayoutSearch;
    @BindView(R.id.searchview)
    android.widget.SearchView searchview;

    private FragmentAdapter adapter;
    FirebaseAuth firebaseAuth;
    ArrayList<User> users = new ArrayList<>();
    UserAdapter userAdapter;
    RecyclerView.LayoutManager layoutManager;
    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.onUpdateMainView();
        rcvSearchViewInit();
        toobar();
        adapter = new FragmentAdapter(getSupportFragmentManager());
        frameLayout.setAdapter(adapter);
        frameLayout.setCurrentItem(0);
        setFragment();
        searchviewUser();

    }

    private void setFragment() {
        navigation.setSelectedItemId(R.id.contact);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.contact:
                                frameLayout.setCurrentItem(0);
                                break;
                            case R.id.itchatgroup:
                                frameLayout.setCurrentItem(1);
                                break;
                            case R.id.itchat:
                                frameLayout.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });
    }

    private void searchviewUser() {
        searchview.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mainPresenter.onSearchUser(s,users);
                return true;
            }
        });
    }

    private void rcvSearchViewInit() {
        layoutManager = new GridLayoutManager(this, 1);
        rcvSearchView.setLayoutManager(layoutManager);
        rcvSearchView.setHasFixedSize(true);
        userAdapter = new UserAdapter(this, R.layout.item_user_searched, users);
        rcvSearchView.setAdapter(userAdapter);
    }

    private void toobar() {
        mainToolbar.setTitle(R.string.toolbarTitle);
        setSupportActionBar(mainToolbar);
    }

    @OnClick(R.id.ic_me)
    public void onViewClicked() {
        startActivity(new Intent(this, MeActivity.class));
        finish();
    }

    @Override
    public void updateSuccess(String avatar) {
        Glide.with(getApplicationContext()).load(avatar).into(icMe);
    }

    @Override
    public void onSearchSuccess(ArrayList<User> users) {
        userAdapter.notifyDataSetChanged();
    }
}
