package im.after.neko.presenter.login;

import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.contract.login.LoginContract;
import im.after.neko.data.api.auth.bean.AuthBean;
import im.after.neko.data.api.auth.bean.AuthErrorBean;
import im.after.neko.data.db.model.TokenModel;
import im.after.neko.data.db.model.TokenModel_Table;
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
        Log.d(TAG, "handleLoginSuccess");
        Log.d(TAG, "=> token: " + authBean.getToken());

        // Find previous token
        TokenModel tokenModel = SQLite.select()
                .from(TokenModel.class)
                .where(TokenModel_Table.id.is(1))
                .querySingle();

        if (tokenModel == null) {
            Log.d(TAG, "=> create new token");

            // Store token if token not exists in database
            TokenModel model = new TokenModel();
            model.setToken(authBean.getToken());
            model.save();
        } else {
            Log.d(TAG, "=> update exists token");

            // Update token if token is exists in database
            tokenModel.setToken(authBean.getToken());
            tokenModel.update();
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
