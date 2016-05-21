package im.after.neko.di.component;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import im.after.neko.data.api.auth.AuthApi;
import im.after.neko.di.module.ApiModule;
import im.after.neko.di.module.ApplicationModule;
import im.after.neko.model.login.LoginModel;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

    // ApplicationModule
    Context context();

    OkHttpClient getOKHttpClient();

    Gson getGson();

    // ApiModule
    AuthApi getAuthApi();

    // ModelModule
    LoginModel getLoginModel();

}
