package im.after.neko.api.login;

import im.after.neko.mvp.bean.AuthBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import rx.Observable;

public interface AuthService {

    @FormUrlEncoded
    @POST("login")
    Observable<AuthBean> login(@Field("username") String username, @Field("password") String password);

}
