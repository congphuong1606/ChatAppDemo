package ominext.android.vn.androidchatexample.Activity.MainActivity;

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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ominext.android.vn.androidchatexample.Activity.MeActivity.Ui.MeActivity;
import ominext.android.vn.androidchatexample.Adapter.FragmentAdapter;
import ominext.android.vn.androidchatexample.Adapter.SearchAdapter;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;
import ominext.android.vn.androidchatexample.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.frame_layout)
    ViewPager frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.ic_me)
    CircleImageView icMe;
    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.btn_creat_new_chat)
    ImageButton btnCreatNewChat;
    @BindView(R.id.rcv_search_view)
    RecyclerView rcvSearchView;
    @BindView(R.id.frame_layout_search)
    FrameLayout frameLayoutSearch;
    private FragmentAdapter adapter;
    FirebaseAuth firebaseAuth;
    ArrayList<User> users = new ArrayList<>();
    SearchAdapter searchAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toobar();
        rcvSearchViewInit();
        frameLayoutSearch.setVisibility(View.GONE);
        firebaseAuth = FirebaseAuth.getInstance();
        adapter = new FragmentAdapter(getSupportFragmentManager());
        frameLayout.setAdapter(adapter);
        frameLayout.setCurrentItem(0);
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
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                DatabaseReference mFirebaseDatabase;
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                String id = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference reference = mFirebaseDatabase.child(Instance.USERS_PATH);
                Query query = reference.orderByChild("name")
                        .startAt(s).endAt(s + "\uf8ff");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            frameLayoutSearch.setVisibility(View.VISIBLE);
                            users.clear();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                User user = postSnapshot.getValue(User.class);
                                String id = user.getId();
                                String name = user.getName();
                                String email=user.getEmail();
                                String avatar = user.getAvatar();
                                String phone = user.getPhoneNumber();
                                boolean online = user.isOnline();
                                Map<String, Boolean> contacts = user.getContacts();
                                users.add(new User(id,name,email,avatar,phone,online,contacts));
                            }
                            searchAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return true;

            }
        });
    }

    private void rcvSearchViewInit() {
        layoutManager = new GridLayoutManager(this, 1);
        rcvSearchView.setLayoutManager(layoutManager);
        rcvSearchView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(this, R.layout.item_user_searched, users);
        rcvSearchView.setAdapter(searchAdapter);
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

}
