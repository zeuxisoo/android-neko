package im.after.app.data.api.dashboard;

import im.after.app.base.BaseApi;
import im.after.app.data.api.AuthorizationInterceptor;
import im.after.app.data.api.dashboard.bean.DashboardBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Observable;

public class DashboardApi extends BaseApi<DashboardService> {

    public DashboardApi(OkHttpClient.Builder okHttpClientBuilder) {
        super(okHttpClientBuilder);
    }

    @Override
    public String getBaseUrl() {
        return "https://www.after.im/api/v1/dashboard/";
    }

    @Override
    public OkHttpClient getClient(OkHttpClient.Builder okHttpClientBuilder) {
        okHttpClientBuilder.interceptors().add(new AuthorizationInterceptor());

        return okHttpClientBuilder.build();
    }

    @Override
    public DashboardService getApiService(Retrofit retrofit) {
        return retrofit.create(DashboardService.class);
    }

    public Observable<DashboardBean> all(int page) {
        return this.getService().all(page);
    }

}
