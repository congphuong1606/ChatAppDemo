package ominext.android.vn.androidchatexample;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import ominext.android.vn.androidchatexample.Lib.GlideImageLoader;
import ominext.android.vn.androidchatexample.Lib.ImageLoader;

/**
 * Created by MyPC on 18/07/2017.
 */

public class AndroidChatApplication extends Application {
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
        setupImageLoader();
    }

    private void setupImageLoader() {
        imageLoader = new GlideImageLoader(this);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private void setupFirebase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }}