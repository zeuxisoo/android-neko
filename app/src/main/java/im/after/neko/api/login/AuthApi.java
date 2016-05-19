package im.after.neko.api.login;

import im.after.neko.mvp.bean.AuthBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

public class AuthApi {

    private static final String BASE_URL = "https://www.after.im/api/v1/auth/";

    private AuthService mAuthService;

    public AuthApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mAuthService = retrofit.create(AuthService.class);
    }

    public Observable<AuthBean> login(String username, String password) {
        return this.mAuthService.login(username, password).subscribeOn(Schedulers.io());
    }

}
