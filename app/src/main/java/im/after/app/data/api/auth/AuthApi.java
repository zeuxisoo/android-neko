package im.after.app.data.api.auth;

import im.after.app.base.BaseApi;
import im.after.app.data.api.auth.bean.AuthBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Observable;

public class AuthApi extends BaseApi<AuthService> {

    public AuthApi(OkHttpClient.Builder okHttpClientBuilder) {
        super(okHttpClientBuilder);
    }

    @Override
    public String getBaseUrl() {
        return "https://www.after.im/api/v1/auth/";
    }

    @Override
    public OkHttpClient getClient(OkHttpClient.Builder okHttpClientBuilder) {
        return okHttpClientBuilder.build();
    }

    @Override
    public AuthService getApiService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    public Observable<AuthBean> login(String account, String password) {
        return this.getService().login(account, password);
    }

}
