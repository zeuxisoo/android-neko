package im.after.neko.mvp.presenter.login;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.mvp.contract.login.LoginContract;
import im.after.neko.mvp.view.login.LoginActivity;

public class LoginPresenter extends BasePresenter implements LoginContract {

    private LoginActivity mLoginActivity;

    @Inject
    public LoginPresenter() {

    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mLoginActivity = (LoginActivity) view;
    }

    // Implementation for LoginContract
    @Override
    public void doLogin(String username, String password) {

    }

    @Override
    public void detachView() {
        this.mLoginActivity = null;
    }

}
