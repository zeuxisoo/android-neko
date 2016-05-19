package im.after.neko.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.neko.api.login.AuthApi;
import okhttp3.OkHttpClient;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public AuthApi provideAuthApi(OkHttpClient okHttpClient) {
        return new AuthApi(okHttpClient);
    }

}
