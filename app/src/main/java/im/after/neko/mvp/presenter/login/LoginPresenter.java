package im.after.neko.mvp.presenter.login;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import im.after.neko.api.login.AuthApi;
import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.mvp.bean.AuthBean;
import im.after.neko.mvp.contract.login.LoginContract;
import im.after.neko.mvp.view.login.LoginActivity;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class LoginPresenter extends BasePresenter implements LoginContract {

    private LoginActivity mLoginActivity;

    private AuthApi mAuthApi;

    private Subscription mSubscription;

    @Inject
    public LoginPresenter(AuthApi authApi) {
        this.mAuthApi = authApi;
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
    public void doLogin(String username, String password) {
        Log.d("LoginActivity", "doLogin");

        this.mSubscription = this.mAuthApi.login(username, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<AuthBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("LoginActivity", "onError");

                    if (e instanceof HttpException) {
                        HttpException httpException = (HttpException) e;

                        ResponseBody response = httpException.response().errorBody();

                        try {
                            // TODO: parse 403 error message
                            Log.d("LoginActivity", response.string());
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }

                    e.printStackTrace();
                }

                @Override
                public void onNext(AuthBean authBean) {
                    Log.d("LoginActivity", "onNext");
                    Log.d("LoginActivity", authBean.token);
                }
            });
    }

}
