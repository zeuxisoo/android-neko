package im.after.neko.di.component;

import android.app.Activity;

import dagger.Component;
import im.after.neko.di.module.ActivityModule;
import im.after.neko.di.scope.ActivityScope;
import im.after.neko.ui.splash.activity.SplashActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    void inject(SplashActivity activity);

}
