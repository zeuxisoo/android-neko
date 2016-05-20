package im.after.neko.api.auth;

import im.after.neko.api.auth.bean.AuthBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import rx.Observable;

public interface AuthService {

    @FormUrlEncoded
    @POST("login")
    Observable<AuthBean> login(@Field("account") String account, @Field("password") String password);

}
