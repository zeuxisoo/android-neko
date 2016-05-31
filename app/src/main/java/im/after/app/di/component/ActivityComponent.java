package im.after.app.di.component;

import android.app.Activity;

import dagger.Component;
import im.after.app.di.module.ActivityModule;
import im.after.app.di.scope.ActivityScope;
import im.after.app.view.dashboard.DashboardActivity;
import im.after.app.view.dashboard.DashboardCreateActivity;
import im.after.app.view.login.LoginActivity;
import im.after.app.view.splash.SplashActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(DashboardActivity activity);

    void inject(DashboardCreateActivity activity);

}
