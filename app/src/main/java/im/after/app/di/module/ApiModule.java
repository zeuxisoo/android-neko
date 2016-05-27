package im.after.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.data.api.auth.AuthApi;
import im.after.app.data.api.dashboard.DashboardApi;
import okhttp3.OkHttpClient;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public AuthApi provideAuthApi(OkHttpClient.Builder okHttpClientBuilder) {
        return new AuthApi(okHttpClientBuilder);
    }

    @Provides
    @Singleton
    public DashboardApi provideDashboardApi(OkHttpClient.Builder okHttpClientBuilder) {
        return new DashboardApi(okHttpClientBuilder);
    }

}
