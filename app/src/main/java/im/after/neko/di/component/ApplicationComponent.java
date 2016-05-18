package im.after.neko.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import im.after.neko.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

}
