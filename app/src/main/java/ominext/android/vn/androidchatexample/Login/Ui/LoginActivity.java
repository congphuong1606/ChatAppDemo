package ominext.android.vn.androidchatexample.Login.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.androidchatexample.Chat.Ui.ChatActivity;
import ominext.android.vn.androidchatexample.Login.LoginPresenter;
import ominext.android.vn.androidchatexample.Login.LoginPresenterImpl;
import ominext.android.vn.androidchatexample.R;
import ominext.android.vn.androidchatexample.Register.Ui.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    LoginPresenter loginPresenter;
    @BindView(R.id.tv_username)
    EditText tvUsername;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_login_email)
    Button btnLoginEmail;
    @BindView(R.id.btn_fb_login)
    Button btnFbLogin;
    @BindView(R.id.tv_lost_pass)
    Button tvLostPass;
    @BindView(R.id.tv_register)
    Button tvRegister;
    private String email;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick({R.id.btn_login_email, R.id.btn_fb_login, R.id.tv_lost_pass, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_email:
                loginPresenter.onSingIn(email, pass);
                break;
            case R.id.btn_fb_login:
                break;
            case R.id.tv_lost_pass:
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onVerified() {

    }

    @Override
    public void onSignInFail(String msg) {
        startActivity(new Intent(LoginActivity.this, ChatActivity.class));
        finish();
    }

    @Override
    public void onViriFail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage(getResources().getString(R.string.verifiation));
        builder.setIcon(R.drawable.logo_chat);
        builder.create().show();
    }

    @Override
    public boolean isCheckInputData() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.addAuth();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.remoteAuth();
    }
}
