package im.after.app.di.component;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import im.after.app.data.api.auth.AuthApi;
import im.after.app.data.api.dashboard.DashboardApi;
import im.after.app.di.module.ApiModule;
import im.after.app.di.module.ApplicationModule;
import im.after.app.di.module.HelperModule;
import im.after.app.helper.MaterialDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.model.dashboard.DashboardModel;
import im.after.app.model.login.LoginModel;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class, HelperModule.class })
public interface ApplicationComponent {

    // ApplicationModule
    Context context();

    OkHttpClient.Builder getOKHttpClientBuilder();

    Gson getGson();

    // ApiModule
    AuthApi getAuthApi();

    DashboardApi getDashboardApi();

    // ModelModule
    LoginModel getLoginModel();

    DashboardModel getDashboardModel();

    // HelperModule
    MaterialDialogHelper getMaterialDialogHelper();

    ToastHelper getToastHelper();

}
