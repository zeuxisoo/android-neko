package im.after.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.presenter.login.LoginPresenter;
import im.after.app.view.dashboard.DashboardActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutLogin)
    RelativeLayout mRelativeLayoutLogin;

    @BindView(R.id.editTextAccount)
    EditText mEditTextAccount;

    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;

    @BindView(R.id.checkboxRememberMe)
    CheckBox mCheckBoxRememberMe;

    @BindView(R.id.buttonLogin)
    Button mButtonLogin;

    @BindView(R.id.circularProgressBarLogin)
    CircularProgressBar mCircularProgressBar;

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
    public void initToolbar() {

    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @OnClick(R.id.buttonLogin)
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
        super.showSnackbar(this.mRelativeLayoutLogin, message, Snackbar.LENGTH_SHORT);
    }

    public void showCircularProgressBar(boolean show) {
        if (show) {
            this.mButtonLogin.setEnabled(false);
            this.mCircularProgressBar.setVisibility(View.VISIBLE);
        }else{
            this.mButtonLogin.setEnabled(true);
            this.mCircularProgressBar.setVisibility(View.INVISIBLE);
        }
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
