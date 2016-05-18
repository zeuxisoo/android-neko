package im.after.neko.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.neko.MyApplication;
import im.after.neko.di.scope.ContextLife;

@Module
public class ApplicationModule {

    private MyApplication mMyApplication;

    public ApplicationModule(MyApplication application) {
        this.mMyApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    public Context provideContext() {
        return this.mMyApplication.getApplicationContext();
    }

}
