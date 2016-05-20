package im.after.neko.di.component;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import im.after.neko.api.auth.AuthApi;
import im.after.neko.di.module.ApiModule;
import im.after.neko.di.module.ApplicationModule;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

    // ApplicationModule
    Context context();

    OkHttpClient getOKHttpClient();

    Gson getGsion();

    // ApiModule
    AuthApi getAuthApi();

}
