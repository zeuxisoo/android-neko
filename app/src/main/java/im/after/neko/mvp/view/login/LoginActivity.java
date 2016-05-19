package im.after.neko.mvp.view.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import im.after.neko.R;
import im.after.neko.base.BaseActivity;
import im.after.neko.mvp.presenter.login.LoginPresenter;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutLogin)
    RelativeLayout mRelativeLayoutLogin;

    @BindView(R.id.editTextUsername)
    EditText mEditTextUsername;

    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;

    @BindView(R.id.rippleLayoutLoginButton)
    View mRippleLayoutLoginButton;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initInjector() {
        this.mActivityComponent.inject(this);
    }

    @Override
    public void initContentView() {
        this.setContentView(R.layout.activity_login);
    }

    @Override
    public void initViewAndListener() {
        MaterialRippleLayout.on(this.mRippleLayoutLoginButton).create();
    }

    @Override
    public void initPresenter() {
        this.mLoginPresenter.attachView(this);
    }

    @OnClick(R.id.rippleLayoutLoginButton)
    public void doLogin() {
        String username = mEditTextUsername.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            this.showSnackbar(this.getString(R.string.error_empty_username));
        }else if (TextUtils.isEmpty(password)) {
            this.showSnackbar(this.getString(R.string.error_empty_password));
        }else {
            this.mLoginPresenter.doLogin(username, password);
        }
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(this.mRelativeLayoutLogin, message, Snackbar.LENGTH_SHORT);

        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackbarBackground));

        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorSnackbarText));

        snackbar.show();
    }

}
