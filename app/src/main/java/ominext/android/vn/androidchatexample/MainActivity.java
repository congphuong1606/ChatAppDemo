package ominext.android.vn.androidchatexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ominext.android.vn.androidchatexample.Adapter.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frame_layout)
    ViewPager frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

    }
}
