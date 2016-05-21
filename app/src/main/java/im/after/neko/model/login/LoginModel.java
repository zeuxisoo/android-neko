package im.after.neko.model.login;

import javax.inject.Inject;

import im.after.neko.data.api.auth.AuthApi;
import im.after.neko.data.api.auth.bean.AuthBean;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginModel {

    public AuthApi mAuthApi;

    @Inject
    public LoginModel(AuthApi authApi) {
        this.mAuthApi = authApi;
    }

    public Subscription doLogin(String account, String password, Action1<AuthBean> success, Action1<Throwable> error) {
        return this.mAuthApi.login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error);
    }

}
