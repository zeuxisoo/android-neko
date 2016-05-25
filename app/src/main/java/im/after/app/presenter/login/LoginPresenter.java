package im.after.app.presenter.login;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;

import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.contract.login.LoginContract;
import im.after.app.data.api.auth.bean.AuthBean;
import im.after.app.data.api.auth.bean.AuthErrorBean;
import im.after.app.data.db.model.AccountModel;
import im.after.app.data.db.model.TokenModel;
import im.after.app.model.login.LoginModel;
import im.after.app.view.login.LoginActivity;
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
    public void loadAccountAndPasswordByRememberMe() {
        AccountModel accountModel = this.mLoginModel.findAccountById(1);

        if (accountModel != null && !accountModel.getAccount().isEmpty() && !accountModel.getPassword().isEmpty()) {
            this.mLoginActivity.setAccountAndPassword(accountModel.getAccount(), accountModel.getPassword());
        }
    }

    @Override
    public void doLogin(String account, String password, boolean isRememberMe) {
        AccountModel accountModel = this.mLoginModel.findAccountById(1);

        // Remember account when remember me is checked
        if (isRememberMe) {
            if (accountModel == null) {
                // Create account if not any records
                this.mLoginModel.createAccount(account, password);
            } else {
                // Update account information if account is exists
                this.mLoginModel.updateAccountAndPasswordByAccountModel(accountModel, account, password);
            }
        }

        // Clear exists remembered account if remember me is not checked, account and password is not empty
        if (!isRememberMe && accountModel != null && !accountModel.getAccount().isEmpty() && !accountModel.getPassword().isEmpty()) {
            this.mLoginModel.clearAccountByAccountModel(accountModel);
        }

        // Show loading animation
        this.mLoginActivity.showCircularProgressBar(true);

        this.mSubscription = this.mLoginModel.doLogin(account, password, this::handleLoginSuccess, this::handleLoginError);
    }

    // Subscribe handler for doLogin method
    private void handleLoginSuccess(AuthBean authBean) {
        Logger.t(TAG).d("=> token: %s", authBean.getToken());

        // Find previous token
        TokenModel tokenModel = this.mLoginModel.findTokenById(1);

        if (tokenModel == null) {
            // Create token if token not exists in database
            this.mLoginModel.createToken(authBean.getToken());
        } else {
            // Update token if token is exists in database
            this.mLoginModel.updateTokenByTokenModel(tokenModel, authBean.getToken());
        }

        this.mLoginActivity.showCircularProgressBar(false);
        this.mLoginActivity.redirectToDashboardActivity();
    }

    private void handleLoginError(Throwable throwable) {
        this.mLoginActivity.showCircularProgressBar(false);

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
