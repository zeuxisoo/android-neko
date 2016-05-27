package im.after.app.di.module;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.MyApplication;
import okhttp3.OkHttpClient;
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
        
        return builder.build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

}
