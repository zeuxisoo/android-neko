package im.after.app.di.module;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.MyApplication;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApplicationModule {

    private MyApplication mMyApplication;

    public ApplicationModule(MyApplication application) {
        this.mMyApplication = application;
    }

    @Provides
    public Context provideApplicationContext() {
        return this.mMyApplication;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);

        // Disable http2 for OkHttp to fix current issue
        // - Ref: https://forums.bitfire.at/topic/1082/nginx-http-2-incompatible-with-okhttp
        // - Ref: https://trac.nginx.org/nginx/ticket/959
        // - Ref: https://github.com/square/okhttp/issues/2543
        builder.protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.SPDY_3));

        return builder.build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

}
