package ominext.android.vn.androidchatexample.Activity.MeActivity;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ominext.android.vn.androidchatexample.Activity.MeActivity.Ui.MeActivityView;
import ominext.android.vn.androidchatexample.Entities.User;
import ominext.android.vn.androidchatexample.Instance;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by MyPC on 19/07/2017.
 */

public class MeActivityPresenterImpl implements MeActivityPresenter {
    private final StorageReference mountainImagesRef;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference mDatabase;
    MeActivityView meActivityView;
    FirebaseAuth firebaseAuth;
    String id;
    private UploadTask uploadTask;

    public MeActivityPresenterImpl(MeActivityView meActivityView) {
        this.meActivityView = meActivityView;
        firebaseAuth = FirebaseAuth.getInstance();
        id = firebaseAuth.getCurrentUser().getUid();
        mountainImagesRef = storageRef.child("images").child(id).child("avatar.jpg");
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public void signOut() {
        firebaseAuth.getInstance().signOut();
        meActivityView.onSignOutSuccess();
    }

    @Override
    public void uploadUserAvatar(String pathImage) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File(pathImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        uploadTask = mountainImagesRef.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                meActivityView.onUpLoadFail("lôi");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                updateUser(downloadUrl);
            }
        });
    }

    @Override
    public void uploadUserAvatar(Uri uri) {

    }

    @Override
    public void updateUser(String downloadUrl) {
        mDatabase.child(Instance.USERS_PATH).child(id).child("avatar").setValue(downloadUrl);
        meActivityView.onUploadSuccess(downloadUrl);
    }

    @Override
    public void onUpdateUserName(String userName) {
        //dataSnapshot.child("ZNAME").getValue(String.class);
    }

    @Override
    public void onUpdateNumberName(String phoneNumber) {
        //dataSnapshot.child("ZNAME").getValue(String.class);
    }

    @Override
    public void onUpdateActivity() {
        DatabaseReference mDatabaseUser = mDatabase.child(Instance.USERS_PATH).child(id);
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String avatar = user.getAvatar();
                String name = user.getName();
                String phone = user.getPhoneNumber();
                String email = user.getEmail();
                meActivityView.onUploadActivitySuccess(avatar, name, phone, email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                meActivityView.onUpLoadFail("lỗi rồi");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void uploadUserAvatar(byte[] b) {
        uploadTask = mountainImagesRef.putBytes(b);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                meActivityView.onUpLoadFail("lôi");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                updateUser(downloadUrl);
            }
        });
    }
}
