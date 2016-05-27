package im.after.app.data.api.dashboard;

import im.after.app.data.api.dashboard.bean.DashboardsBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class DashboardApi {

    private static final String BASE_URL = "https://www.after.im/api/v1/dashboard/";

    private DashboardService mDashboardService;

    public DashboardApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mDashboardService = retrofit.create(DashboardService.class);
    }

    public Observable<DashboardsBean> all(int page) {
        return this.mDashboardService.all(page);
    }

}
