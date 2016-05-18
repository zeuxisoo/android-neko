package im.after.neko.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import im.after.neko.di.module.ApplicationModule;
import im.after.neko.di.scope.ContextLife;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getContext();

}
