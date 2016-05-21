package im.after.neko.presenter.login;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.contract.login.LoginContract;
import im.after.neko.data.api.auth.bean.AuthBean;
import im.after.neko.data.api.auth.bean.AuthErrorBean;
import im.after.neko.data.db.model.TokenModel;
import im.after.neko.model.login.LoginModel;
import im.after.neko.view.login.LoginActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

public class LoginPresenter extends BasePresenter implements LoginContract {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginActivity mLoginActivity;

    private LoginModel mLoginModel;
    private Gson mGson;

    private Subscription mSubscription;

    @Inject
    public LoginPresenter(LoginModel loginModel, Gson gson) {
        this.mLoginModel = loginModel;
        this.mGson       = gson;
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
        this.mSubscription = this.mLoginModel.doLogin(account, password, this::handleLoginSuccess, this::handleLoginError);
    }

    // Subscribe handler for doLogin method
    private void handleLoginSuccess(AuthBean authBean) {
        Logger.t(TAG).d("handleLoginSuccess");
        Logger.t(TAG).d("=> token: %s", authBean.getToken());

        // Find previous token
        TokenModel tokenModel = this.mLoginModel.findTokenById(1);

        if (tokenModel == null) {
            Logger.t(TAG).d("=> create new token");

            // Create token if token not exists in database
            this.mLoginModel.createToken(authBean.getToken());
        } else {
            Logger.t(TAG).d("=> update exists token");

            // Update token if token is exists in database
            this.mLoginModel.updateTokenByTokenModel(tokenModel, authBean.getToken());
        }
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
