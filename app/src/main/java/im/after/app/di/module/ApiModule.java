package im.after.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.data.api.auth.AuthApi;
import okhttp3.OkHttpClient;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public AuthApi provideAuthApi(OkHttpClient okHttpClient) {
        return new AuthApi(okHttpClient);
    }

}
