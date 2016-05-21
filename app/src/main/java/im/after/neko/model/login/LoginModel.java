package im.after.neko.model.login;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import javax.inject.Inject;

import im.after.neko.data.api.auth.AuthApi;
import im.after.neko.data.api.auth.bean.AuthBean;
import im.after.neko.data.db.model.TokenModel;
import im.after.neko.data.db.model.TokenModel_Table;
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

    public TokenModel findTokenById(long id) {
        return SQLite.select().from(TokenModel.class).where(TokenModel_Table.id.is(id)).querySingle();
    }

    public void createToken(String token) {
        TokenModel model = new TokenModel();
        model.setToken(token);
        model.save();
    }

    public void updateTokenByTokenModel(TokenModel tokenModel, String token) {
        tokenModel.setToken(token);
        tokenModel.update();
    }

}
