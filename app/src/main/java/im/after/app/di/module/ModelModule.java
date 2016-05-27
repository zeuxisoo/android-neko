package im.after.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.data.api.auth.AuthApi;
import im.after.app.data.api.dashboard.DashboardApi;
import im.after.app.model.dashboard.DashboardModel;
import im.after.app.model.login.LoginModel;

@Module
public class ModelModule {

    @Provides
    @Singleton
    public LoginModel provideLoginModel(AuthApi authApi) {
        return new LoginModel(authApi);
    }

    @Provides
    @Singleton
    public DashboardModel provideDashboardModel(DashboardApi dashboardApi) {
        return new DashboardModel(dashboardApi);
    }

}
