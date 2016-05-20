package im.after.neko.mvp.presenter.login;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import im.after.neko.api.login.AuthApi;
import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.mvp.bean.AuthBean;
import im.after.neko.mvp.bean.AuthErrorBean;
import im.after.neko.mvp.contract.login.LoginContract;
import im.after.neko.mvp.view.login.LoginActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class LoginPresenter extends BasePresenter implements LoginContract {

    private LoginActivity mLoginActivity;

    private AuthApi mAuthApi;
    private Gson mGson;

    private Subscription mSubscription;

    @Inject
    public LoginPresenter(AuthApi authApi, Gson gson) {
        this.mAuthApi = authApi;
        this.mGson    = gson;
    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mLoginActivity = (LoginActivity) view;
    }

    @Override
    public void detachView() {
        this.mLoginActivity = null;

        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
    }

    // Implementation for LoginContract
    @Override
    public void doLogin(String account, String password) {
        Log.d("LoginActivity", "doLogin");

        this.mSubscription = this.mAuthApi.login(account, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleLoginSuccess, this::handleLoginError);
    }

    // Subscribe handler for doLogin method
    private void handleLoginSuccess(AuthBean authBean) {
        Log.d("LoginActivity", authBean.getToken());
    }

    private void handleLoginError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            try {
                AuthErrorBean authErrorBean = this.mGson.fromJson(httpException.response().errorBody().string(), AuthErrorBean.class);

                this.mLoginActivity.showSnackbar(
                    String.format("%s - %s", authErrorBean.getStatusCode(), authErrorBean.getMessage())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throwable.printStackTrace();
        }
    }

}
