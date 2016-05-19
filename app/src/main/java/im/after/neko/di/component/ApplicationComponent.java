package im.after.neko.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import im.after.neko.api.login.AuthApi;
import im.after.neko.di.module.ApiModule;
import im.after.neko.di.module.ApplicationModule;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

    // ApplicationModule
    Context context();

    OkHttpClient getOKHttpClient();

    // ApiModule
    AuthApi getAuthApi();

}
