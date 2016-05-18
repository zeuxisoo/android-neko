package im.after.neko.di.component;

import android.content.Context;

import dagger.Component;
import im.after.neko.di.module.ActivityModule;
import im.after.neko.di.scope.ActivityScope;
import im.after.neko.di.scope.ContextLife;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

}
