package ominext.android.vn.androidchatexample.Activity.MainActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ominext.android.vn.androidchatexample.Activity.MainActivity.Ui.MainView;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;

/**
 * Created by MyPC on 20/07/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    MainView mainView;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String id;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        firebaseAuth = FirebaseAuth.getInstance();
        id = firebaseAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onUpdateMainView() {
        DatabaseReference ref = reference.child(Instance.USERS_PATH).child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String avatar = user.getAvatar();
                if (avatar != null) {
                    mainView.updateSuccess(avatar);
                } else avatar = ("/drawable/avatar.jpg");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSearchUser(final String s, final ArrayList<User> users) {
        DatabaseReference refChildName = reference.child(Instance.USERS_PATH);
        Query query = refChildName.orderByChild("name").equalTo(s);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);

                }
                mainView.onSearchSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
