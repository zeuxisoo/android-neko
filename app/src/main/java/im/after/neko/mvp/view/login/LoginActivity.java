package im.after.neko.mvp.view.login;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import im.after.neko.R;
import im.after.neko.base.BaseActivity;
import im.after.neko.mvp.presenter.login.LoginPresenter;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutLogin)
    RelativeLayout mRelativeLayoutLogin;

    @BindView(R.id.editTextAccount)
    EditText mEditTextAccount;

    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;

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
    }

    @Override
    public void initPresenter() {
        this.mLoginPresenter.attachView(this);
    }

    @OnClick(R.id.rippleLayoutLoginButton)
    public void doLogin() {
        String account = mEditTextAccount.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            this.showSnackbar(this.getString(R.string.error_empty_account));
        }else if (TextUtils.isEmpty(password)) {
            this.showSnackbar(this.getString(R.string.error_empty_password));
        }else {
            this.mLoginPresenter.doLogin(account, password);
        }
    }

    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(this.mRelativeLayoutLogin, message, Snackbar.LENGTH_SHORT);

        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackbarBackground));

        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorSnackbarText));

        snackbar.show();
    }

}
