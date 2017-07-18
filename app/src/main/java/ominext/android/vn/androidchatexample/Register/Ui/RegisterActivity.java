package ominext.android.vn.androidchatexample.Register.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.androidchatexample.BaseActivity;
import ominext.android.vn.androidchatexample.Login.Ui.LoginActivity;
import ominext.android.vn.androidchatexample.R;
import ominext.android.vn.androidchatexample.Register.RegisterPresenter;
import ominext.android.vn.androidchatexample.Register.RegisterPresenterImpl;
import ominext.android.vn.androidchatexample.Utils.Util;

public class RegisterActivity extends BaseActivity implements RegisterView {

    RegisterPresenter registerPresenter;
    @BindView(R.id.tv_email)
    EditText tvEmail;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_login_email)
    Button btnLoginEmail;
    @BindView(R.id.btn_reset_password)
    TextView btnResetPassword;
    @BindView(R.id.btn_back_login)
    Button btnBackLogin;
    private String email;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void onRegisSusscess() {
        hideProgressDialog();
        tvEmail.setText("");
        tvPassword.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage(getResources().getString(R.string.verifiation));
        builder.setIcon(R.drawable.logo_chat);
        builder.create().show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCheckInputData() {
        if (Util.isEmpty(tvEmail) && Util.isEmpty(tvPassword)) {
            email = tvEmail.getText().toString().trim();
            pass = tvPassword.getText().toString().trim();
            if (!Util.isEmailValid(email)) {
                tvEmail.requestFocus();
                tvEmail.setError(getResources().getString(R.string.email_error));
                return false;
            } else {
                if (pass.length() < 6) {
                    tvEmail.requestFocus();
                    tvEmail.setError(getResources().getString(R.string.pass_error));
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRegisFail(String msg) {
        hideProgressDialog();
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_login_email, R.id.btn_reset_password, R.id.btn_back_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_email:
                if (onCheckInputData()) {
                    showProgressDialog("Vui lòng đợi");
                    registerPresenter.registerNewUser(email, pass);
                }
                break;
            case R.id.btn_reset_password:
                break;
            case R.id.btn_back_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
