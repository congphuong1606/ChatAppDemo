package ominext.android.vn.androidchatexample.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ominext.android.vn.androidchatexample.Activity.Chat.Ui.ChatFragment;
import ominext.android.vn.androidchatexample.Activity.Contact.Ui.ContactFragment;
import ominext.android.vn.androidchatexample.Activity.Home.Ui.HomeFragment;

/**
 * Created by MyPC on 19/07/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ContactFragment.newInstance();

            case 2:
                return ChatFragment.newInstance();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}