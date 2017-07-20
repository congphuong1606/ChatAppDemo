package ominext.android.vn.androidchatexample.Activity.MeActivity.Ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ominext.android.vn.androidchatexample.Activity.Login.Ui.LoginActivity;
import ominext.android.vn.androidchatexample.Activity.MainActivity.MainActivity;
import ominext.android.vn.androidchatexample.Activity.MeActivity.MeActivityPresenter;
import ominext.android.vn.androidchatexample.Activity.MeActivity.MeActivityPresenterImpl;
import ominext.android.vn.androidchatexample.R;

public class MeActivity extends AppCompatActivity implements MeActivityView {
    MeActivityPresenter meActivityPresenter;
    @BindView(R.id.tv_dang_xuat)
    TextView tvDangXuat;
    @BindView(R.id.civ_back_home)
    CircleImageView civBackHome;
    @BindView(R.id.me_toolbar)
    Toolbar meToolbar;
    @BindView(R.id.imv_avatar)
    CircleImageView imvAvatar;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    private static final int REQUEST_CHOOSE_PHOTO = 0;
    private static final int REQUEST_TAKE_PHOTO = 1;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email_user)
    TextView tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);
        meActivityPresenter = new MeActivityPresenterImpl(this);
        meActivityPresenter.onUpdateActivity();
        toobar();
    }

    private void toobar() {
        meToolbar.setTitle(R.string.toolbarTitle);
        setSupportActionBar(meToolbar);
    }

    @OnClick({R.id.tv_dang_xuat, R.id.civ_back_home, R.id.imv_avatar
            , R.id.tv_email_user,R.id.tv_name, R.id.tv_number, R.id.tv_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                break;
            case R.id.tv_email_user:
                break;
            case R.id.tv_dang_xuat:
                meActivityPresenter.signOut();
                break;
            case R.id.civ_back_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.imv_avatar:
                selectPhoto();
                break;
            case R.id.tv_number:
                break;
            case R.id.tv_pass:
                break;
        }
    }

    private void selectPhoto() {
        final CharSequence[] items = {"chọn hình", "chụp hình"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.titleavatar));
        builder.setIcon(R.drawable.ic_avatar);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("chọn hình")) {
                    chossePhoto();
                } else if (items[i].equals("chụp hình")) {
                    takePicture();
                }
            }
        });
        builder.setPositiveButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this
                        ,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    private void chossePhoto() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                , REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onSignOutSuccess() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onUpLoadFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadSuccess(String downloadUrl) {
        Glide.with(this).load(downloadUrl).into(imvAvatar);
        Toast.makeText(this, downloadUrl, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadActivitySuccess(String avatar, String name, String phone,String email) {
        if (avatar != null) {
            Glide.with(this).load(avatar).into(imvAvatar);
        }
        if (phone != null) {
            tvNumber.setText("số điện thoại: " + phone);
        }
        tvName.setText("-<- " + name + " ->--");
        tvEmail.setText("email: "+email);
    }

    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                if (mCurrentPhotoPath != null) {
                    meActivityPresenter.uploadUserAvatar(mCurrentPhotoPath);
                }
            } else if (requestCode == REQUEST_CHOOSE_PHOTO && data != null) {
                Uri uri = data.getData();
                Bitmap bitmap=null;
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, imageByte);
                byte[] b = imageByte.toByteArray();
                meActivityPresenter.uploadUserAvatar(b);


            }

        }

    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
