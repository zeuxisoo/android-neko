package im.after.neko.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import im.after.neko.R;
import im.after.neko.base.BaseActivity;
import im.after.neko.presenter.login.LoginPresenter;
import im.after.neko.view.dashboard.DashboardActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutLogin)
    RelativeLayout mRelativeLayoutLogin;

    @BindView(R.id.editTextAccount)
    EditText mEditTextAccount;

    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;

    @BindView(R.id.checkboxRememberMe)
    CheckBox mCheckBoxRememberMe;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        this.mLoginPresenter.detachView();

        super.onDestroy();
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
        this.mLoginPresenter.loadAccountAndPasswordByRememberMe();
    }

    @Override
    public void initPresenter() {
        this.mLoginPresenter.attachView(this);
    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @OnClick(R.id.rippleLayoutLoginButton)
    public void doLogin() {
        String account = mEditTextAccount.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        boolean rememberMe = mCheckBoxRememberMe.isChecked();

        if (TextUtils.isEmpty(account)) {
            this.showSnackbar(this.getString(R.string.error_empty_account));
        }else if (TextUtils.isEmpty(password)) {
            this.showSnackbar(this.getString(R.string.error_empty_password));
        }else {
            this.mLoginPresenter.doLogin(account, password, rememberMe);
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

    public void setAccountAndPassword(String account, String password) {
        this.mEditTextAccount.setText(account);
        this.mEditTextPassword.setText(password);
        this.mCheckBoxRememberMe.setChecked(true);
    }

    public void redirectToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}
