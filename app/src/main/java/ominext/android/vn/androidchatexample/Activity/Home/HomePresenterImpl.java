package ominext.android.vn.androidchatexample.Activity.Home;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Activity.Home.Ui.HomeView;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;

/**
 * Created by MyPC on 18/07/2017.
 */

public class HomePresenterImpl implements HomePresenter {
    HomeView homeView;
    DatabaseReference reference;


    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
        reference = FirebaseDatabase.getInstance().getReference()
                .child(Instance.USERS_PATH);
    }

    @Override
    public void onUpdateUserList(final ArrayList<User> users) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    users.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        users.add(user);

                    }
                    homeView.onRecycleViewSuccsess(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
